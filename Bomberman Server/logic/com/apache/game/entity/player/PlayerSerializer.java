/* 
 * This file is part of Bomberman.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.game.entity.player;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.apache.game.GameService;
import com.apache.game.entity.attrib.AttributeKey;
import com.apache.game.entity.attrib.AttributeValue;
import com.apache.map.Location;
import com.apache.net.codec.login.LoginResponse;
import com.apache.util.Utility;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Functions that allow for synchronous and asynchronous serialization and
 * deserialization of a {@link Player}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class PlayerSerializer {

    /**
     * The {@link Path} to all of the serialized {@link Player} data.
     */
    private static final Path FILE_DIR = Paths.get("./data/saved_players");

    /**
     * The {@link Player} being serialized or deserialized.
     */
    private final Player player;

    /**
     * The {@link Path} to the character file.
     */
    private final Path path;

    /**
     * Creates a new {@link PlayerSerializer}.
     *
     * @param player The {@link Player} being serialized or deserialized.
     */
    public PlayerSerializer(Player player) {
        this.player = player;
        path = FILE_DIR.resolve(player.getUsername() + ".json");
    }

    static {
        if (Files.notExists(FILE_DIR)) {
            try {
                Files.createDirectory(FILE_DIR);
            } catch (Exception e) {
                Utility.log(e.getMessage());
            }
        }
    }

    /**
     * Attempts to serialize all persistent data for the {@link Player}.
     */
    public void save() {
        Map<String, Object> mainTable = new LinkedHashMap<>();

        mainTable.put("password", player.getPassword());
        mainTable.put("location", player.getLocation());

        Map<String, Object> attrTable = new HashMap<>();

        // TODO: Lightweight attribute map as Java.util.map is overhead heavy.
        for (Entry<String, AttributeValue<?>> it : player.attributes) {
            AttributeKey<?> key = AttributeKey.ALIASES.get(it.getKey());
            AttributeValue<?> value = it.getValue();

            if (key.isPersistent()) {
                Map<String, Object> attrSubTable = new LinkedHashMap<>();
                attrSubTable.put("type", key.getTypeName());
                attrSubTable.put("value", value.get());

                attrTable.put(key.getName(), attrSubTable);
            }
        }
        mainTable.put("attributes", attrTable);

        try (Writer writer = new FileWriter(path.toFile())) {
            writer.write(Utility.GSON.toJson(mainTable));
        } catch (IOException e) {
            Utility.log(e.getMessage());
        }
    }

    /**
     * Attempts to asynchronously serialize all persistent data for the
     * {@link Player}. Returns a {@link ListenableFuture} detailing the progress
     * and result of the asynchronous task.
     *
     * @param service The {@link GameService} to use for asynchronous execution.
     * @return The {@link ListenableFuture} detailing progress and the result.
     */
    public ListenableFuture<Void> asyncSave(GameService service) {
        return service.submit((Callable<Void>) () -> {
            save();
            return null;
        });
    }

    /**
     * Attempts to deserialize all persistent data for the {@link Player}.
     *
     * @param expectedPassword The expected password to be compared against the
     * deserialized password.
     * @return The {@link LoginResponse} determined by the deserialization.
     */
    public LoginResponse load(String expectedPassword) {
        if (!Files.exists(path)) {
            return LoginResponse.NORMAL;
        }
        try (Reader reader = new FileReader(path.toFile())) {
            JsonObject jsonReader = (JsonObject) new JsonParser().parse(reader);

            String password = jsonReader.get("password").getAsString();
            if (!expectedPassword.equals(password)) {
                return LoginResponse.INVALID_CREDENTIALS;
            }

            Location location = Utility.getAsType(jsonReader.get("location"), Location.class);
            player.setLocation(location);

            JsonObject attr = jsonReader.get("attributes").getAsJsonObject();
            for (Entry<String, JsonElement> it : attr.entrySet()) {
                JsonObject attrReader = it.getValue().getAsJsonObject();

                Class<?> type = Class.forName(attrReader.get("type").getAsString());
                Object data = Utility.getAsType(attrReader.get("value"), type);

                player.attr().get(it.getKey()).set(data);
            }
        } catch (Exception e) {
            Utility.log(e.getMessage());
            return LoginResponse.COULD_NOT_COMPLETE_LOGIN;
        }
        return LoginResponse.NORMAL;
    }

    /**
     * Attempts to asynchronously deserialize all persistent data for the
     * {@link Player}. Returns a {@link ListenableFuture} detailing the progress
     * and result of the asynchronous task.
     *
     * @param expectedPassword The expected password to be compared against the
     * deserialized password.
     * @param service The {@link GameService} to use for asynchronous execution.
     * @return The {@link ListenableFuture} detailing progress and the result.
     */
    public ListenableFuture<LoginResponse> asyncLoad(String expectedPassword, GameService service) {
        return service.submit(() -> load(expectedPassword));
    }
}
