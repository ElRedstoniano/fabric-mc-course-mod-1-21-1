package net.kaupenjoe.mccourse.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {
    public static final KeyBinding K_KEYBIND = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.mccourse.k", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, KeyBinding.Category.MISC));

    public static void registerKeys() {

    }
}
