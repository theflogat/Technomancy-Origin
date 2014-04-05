package democretes.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemNBTHelper {

    public static NBTTagCompound getNBT(ItemStack stack) {
        initNBT(stack);
        return stack.getTagCompound();
    }

    public static void initNBT(ItemStack stack) {
        if (detectNBT(stack)) {
            injectNBT(stack, new NBTTagCompound());
        }
    }

    public static void injectNBT(ItemStack stack, NBTTagCompound nbt) {
        stack.setTagCompound(nbt);
    }

    public static boolean detectNBT(ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static boolean confirmNBT(ItemStack stack, String tag) {
        return getNBT(stack).hasKey(tag);
    }

    // Boolean
    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        getNBT(stack).setBoolean(tag, b);
    }

    public static boolean getBoolean(ItemStack stack, String tag) {
        return getNBT(stack).hasKey(tag);
    }

    // Byte
    public static void setByte(ItemStack stack, String tag, byte b) {
        getNBT(stack).setByte(tag, b);
    }

    public static byte getByte(ItemStack stack, String tag, byte expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getByte(tag) : expected;
    }

    // Short
    public static void setShort(ItemStack stack, String tag, short s) {
        getNBT(stack).setShort(tag, s);
    }

    public static short getshort(ItemStack stack, String tag, short expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getShort(tag) : expected;
    }

    // Int
    public static void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).setInteger(tag, i);
    }

    public static int getInteger(ItemStack stack, String tag, int expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getInteger(tag)
                : expected;
    }

    // Long
    public static void setLong(ItemStack stack, String tag, long l) {
        getNBT(stack).setLong(tag, l);
    }

    public static long getLong(ItemStack stack, String tag, long expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getLong(tag) : expected;
    }

    // Float
    public static void setFloat(ItemStack stack, String tag, float f) {
        getNBT(stack).setFloat(tag, f);
    }

    public static float getFloat(ItemStack stack, String tag, float expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getLong(tag) : expected;
    }

    // Double
    public static void setDouble(ItemStack stack, String tag, double d) {
        getNBT(stack).setDouble(tag, d);
    }

    public static double getDouble(ItemStack stack, String tag, double expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getDouble(tag) : expected;
    }

    // String
    public static void setString(ItemStack stack, String tag, String s) {
        getNBT(stack).setString(tag, s);
    }

    public static String getString(ItemStack stack, String tag, String expected) {
        return confirmNBT(stack, tag) ? getNBT(stack).getString(tag) : expected;
    }

    // Compound
    public static void setCompound(ItemStack stack, String tag,
            NBTTagCompound cmp) {
        getNBT(stack).setCompoundTag(tag, cmp);
    }

    public static NBTTagCompound getCompound(ItemStack stack, String tag,
            String expected, boolean nullify) {
        return confirmNBT(stack, tag) ? getNBT(stack).getCompoundTag(tag)
                : nullify ? null : new NBTTagCompound();
    }

}
