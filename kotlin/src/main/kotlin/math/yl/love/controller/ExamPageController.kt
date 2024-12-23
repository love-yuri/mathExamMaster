package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.ExamPage
import math.yl.love.database.domain.params.examPage.ReleasePageParam
import math.yl.love.database.domain.params.system.GenerateParam
import math.yl.love.database.mapper.ExamPageMapper
import math.yl.love.database.service.ExamPageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exam/page")
@Tag(name = "试卷表")
class ExamPageController: BaseController<ExamPage, ExamPageMapper, ExamPageService>() {

    @PostMapping("release")
    @Operation(summary = "发布试卷")
    fun release(@RequestBody param: ReleasePageParam) = R.success(baseService.release(param))

    @PostMapping("update/page")
    @Operation(summary = "更新试卷")
    fun updatePage(@RequestBody param: ReleasePageParam) = R.success(baseService.updatePage(param))

    @PostMapping("page/simple")
    @Operation(summary = "分页")
    fun pageSimple(@RequestBody param: PageParam) = R.success(baseService.pageSimple(param.current, param.size))

    @PostMapping("detail")
    @Operation(summary = "查找详细信息")
    fun generate(@RequestBody id: Long) = R.success(baseService.detail(id))
}