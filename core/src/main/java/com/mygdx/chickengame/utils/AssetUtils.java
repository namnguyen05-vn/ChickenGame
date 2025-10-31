package com.mygdx.chickengame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Lớp tiện ích để quản lý tài nguyên (Assets) trong game.
 * Cung cấp các phương thức chung để áp dụng filter và giải phóng tài nguyên.
 */
public class AssetUtils {

    /**
     * Áp dụng bộ lọc Linear Filter cho các texture
     * để làm mượt hình ảnh khi phóng to hoặc thu nhỏ.
     *
     * @param textures Danh sách các texture cần áp dụng filter
     */
    public static void applyLinearFilter(Texture... textures) {
        for (Texture texture : textures) {
            if (texture != null) {
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }
        }
    }

    /**
     * Giải phóng tài nguyên (Disposable) để tránh memory leak.
     *
     * @param resources Danh sách các tài nguyên cần giải phóng
     */
    public static void disposeResources(Disposable... resources) {
        for (Disposable resource : resources) {
            if (resource != null) {
                resource.dispose();
            }
        }
    }
}

