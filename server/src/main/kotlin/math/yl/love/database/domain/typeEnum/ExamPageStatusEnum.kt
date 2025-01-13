package math.yl.love.database.domain.typeEnum

import com.aspose.imaging.ImageOptionsBase
import com.aspose.imaging.imageoptions.JpegOptions
import com.aspose.imaging.imageoptions.PngOptions
import com.baomidou.mybatisplus.annotation.IEnum
import org.springframework.http.MediaType

enum class ExamPageStatusEnum(
    val type: Int
): IEnum<Int> {
    // 未开始
    NOT_START(0),

    // 进行中
    DOING(1),

    // 已结束
    FINISHED(2);

    override fun getValue(): Int {
        return type
    }
}

