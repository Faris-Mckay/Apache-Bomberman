package com.apache.maps;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import com.apache.Settings;

public class GenericMap extends TiledMap {

    private ArrayList<Rectangle> tiles = new ArrayList<Rectangle>();

    private boolean blocked[][];

    public GenericMap() throws SlickException {
        super("res/bomberman.tmx");
        init();
    }

    private void init() {
        // This will create an Array with all the Tiles in your map. When set to true, it means that Tile is blocked.
        blocked = new boolean[this.getWidth()][this.getHeight()];

        // Loop through the Tiles and read their Properties
        // Set here the Layer you want to Read. In your case, it'll be layer 1,
        // since the objects are on the second layer.
        int layer = getLayerIndex("Objects");

        //System.out.println("LayerId:  " + layer);
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {

                // Read a Tile
                int tileID = getTileId(i, j, layer);

                // Get the value of the Property named "blocked"
                String value = getTileProperty(tileID, "blocked", "false");

                // If the value of the Property is "true"...
                if (value.equalsIgnoreCase("true")) {
		        	//System.out.println("blocked");

                    // We set that index of the TileMap as blocked
                    blocked[i][j] = true;

                    // And create the collision Rectangle
                    tiles.add(new Rectangle((float) i * Settings.TILE_SIZE_DEFAULT, (float) j * Settings.TILE_SIZE_DEFAULT, Settings.TILE_SIZE_DEFAULT, Settings.TILE_SIZE_DEFAULT));
                } else {
                    //System.out.println("not blocked");
                }
            }
        }
    }

    public ArrayList<Rectangle> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Rectangle> tiles) {
        this.tiles = tiles;
    }

}
