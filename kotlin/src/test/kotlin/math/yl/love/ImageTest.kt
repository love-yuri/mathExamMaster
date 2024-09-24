package math.yl.love

import com.aspose.imaging.Image
import com.aspose.imaging.License
import com.aspose.imaging.fileformats.wmf.WmfImage
import com.aspose.imaging.imageoptions.JpegOptions
import kotlin.test.Test

class ImageTest {

    @Test
    fun test() {
        val license = License()
        license.setLicense("".byteInputStream())
        val image = Image.load("/home/yuri/love-yuri/yuri2078/extracted_image.wmf")
        val option = JpegOptions()
        image.save("a.jpg", option)
        image.dispose()
    }
}