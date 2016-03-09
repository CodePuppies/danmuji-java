package com.codepuppies.danmuji.util;

/**
 * 工具类：字节数组与数值之间的转换
 *
 * @version 2016-3-3
 */
public class ByteNum {
    /**
     * 将short转变为byte数组。short为2个字节，从offset处开始依次写入。
     *
     * @param bytes  目标byte数组
     * @param offset 写入开始处
     * @param value  需要转换的值
     */
    public static void shortToByteArray(byte[] bytes, int offset, short value) {
        bytes[offset] = (byte) ((value >> 8) & 0x00FF);
        bytes[offset + 1] = (byte) (value & 0x00FF);
    }

    /**
     * 从byte数组中读入short。short为两个字节，从offset处读取两个字节。
     *
     * @param bytes  源
     * @param offset 开始位置
     * @return 转换的short值
     */
    public static short shortFromByteArray(byte[] bytes, int offset) {
        return (short) (((bytes[offset] << 8) & 0xFF00) | (bytes[offset + 1] & 0x00FF));
    }

    /**
     * 将int转换为byte数组。int为4个字节，从offset处开始依次写入。
     *
     * @param bytes  目标byte数组
     * @param offset 写入开始处
     * @param value  需要写入的值
     */
    public static void intToByteArray(byte[] bytes, int offset, int value) {
        bytes[offset + 0] = (byte) (value >> 24 & 0x00FF);
        bytes[offset + 1] = (byte) (value >> 16 & 0x00FF);
        bytes[offset + 2] = (byte) (value >> 8 & 0x00FF);
        bytes[offset + 3] = (byte) (value & 0x00FF);
    }

    /**
     * 从byte数组中读入int。int为两个字节，从offset处依次读取。
     *
     * @param bytes  源
     * @param offset 读取开始处
     * @return 转换的int值
     */
    public static int intFromByteArray(byte bytes[], int offset) {
        return ((bytes[offset + 0] & 0xFF) << 24)
                + ((bytes[offset + 1] & 0xFF) << 16)
                + ((bytes[offset + 2] & 0xFF) << 8)
                + (bytes[offset + 3] & 0xFF);
    }

    /**
     * 将float转换为byte数组
     *
     * @param bytes  目标byte数组
     * @param offset 写入开始处
     * @param value  需要转换的值
     */
    public static void floatToByteArray(byte[] bytes, int offset, float value) {
        intToByteArray(bytes, offset, Float.floatToIntBits(value));
    }

    /**
     * 将byte转换为float
     *
     * @param array  源
     * @param offset 开始位置
     * @return 转换后的float值
     */
    public static float floatFromByteArray(byte array[], int offset) {
        return Float.intBitsToFloat(intFromByteArray(array, offset));
    }
}
