package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.annotation.IEnum

/**
 * 文件类型
 * @param code 类型编码
 * @param suffix 支持的文件后缀名
 */
enum class FileTypeEnum (
    private val code: Int,
    @Suppress("MemberVisibilityCanBePrivate")
    val suffix: List<String>
) : IEnum<Int> {
    OTHER(0, listOf()),
    IMAGE(1, listOf("png", "jpg", "jpeg", "gif", "bmp", "tiff", "svg")),
    VIDEO(2, listOf("mp4", "avi", "mkv", "mov", "flv", "wmv")),
    AUDIO(3, listOf("mp3", "wav", "flac", "aac", "ogg", "m4a")),
    DOCUMENT(4, listOf("doc", "docx", "odt", "rtf")),
    ARCHIVE(5, listOf("zip", "rar", "7z", "tar", "gz")),
    SPREADSHEET(6, listOf("xls", "xlsx", "ods", "csv")),
    PRESENTATION(7, listOf("ppt", "pptx", "odp")),
    PDF(8, listOf("pdf")),
    TEXT(9, listOf("txt", "md")),
    EXECUTABLE(10, listOf("exe", "bin", "sh", "bat")),
    SCRIPT(11, listOf("py", "js", "rb", "sh"));

    /**
     * 获取文件类型的编码
     */
    override fun getValue(): Int {
        return code
    }


    companion object {
        fun getFileTypeByName(fileName: String): FileTypeEnum {
            val fileExtension = fileName.substringAfterLast('.', "").lowercase()

            return entries.find { fileType ->
                fileType.suffix.contains(fileExtension)
            } ?: OTHER // 如果没有匹配的类型，返回 OTHER
        }
    }
}
