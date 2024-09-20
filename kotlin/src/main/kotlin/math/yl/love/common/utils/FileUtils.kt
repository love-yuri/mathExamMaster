package math.yl.love.common.utils

import java.io.File
import java.io.InputStream
import java.security.MessageDigest

/**
 * 常用文件工具
 */
object FileUtils {

    /**
     * 计算文件md5, 输入流需要自行关闭
     */
    fun getMd5(inputStream: InputStream): String {
        val digest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(8192)
        var bytesRead: Int
        inputStream.use { inStream ->
            // 读取文件内容并更新 MD5 摘要
            while (inStream.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
        }
        // 将 MD5 摘要转换为十六进制字符串
        return digest.digest().joinToString("") { byte -> "%02x".format(byte) }
    }

    /**
     * @return 文件md5
     */
    fun File.getMd5() = inputStream().use { getMd5(it) }
}