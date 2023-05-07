package app.controllers

import app.models.Auditorium
import app.models.LessonLocation
import app.repositories.AuditoriumRepository
import app.repositories.LessonLocationRepository
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.io.File

@Controller
class ImportController {
    @Autowired
    lateinit var lessonLocationRepository: LessonLocationRepository

    @Autowired
    lateinit var auditoriumRepository: AuditoriumRepository

    @GetMapping("/import")
    fun import() {
//        val workbook = WorkbookFactory.create(File("C:\\Users\\roman\\Downloads\\auditorium-3.xls"))
//        val iterator = workbook.getSheetAt(0).rowIterator()
//        iterator.next()
//        while (iterator.hasNext()) {
//            val row = iterator.next()
//            try {
//                if (row.getCell(2).toString().isEmpty()) continue
//            } catch (_: Exception) {
//                continue
//            }
//
//            val a = Auditorium()
//            a.auditoriumNumber = row.getCell(0).toString().split(".")[0]
//            var location = lessonLocationRepository.findOneByLessonLocation(row.getCell(7).toString())
//            if (location == null) {
//                val newLocation = LessonLocation()
//                newLocation.lessonLocation = row.getCell(7).toString()
//                lessonLocationRepository.save(newLocation)
//                location = lessonLocationRepository.findOneByLessonLocation(row.getCell(7).toString())
//            }
//            a.lessonLocation = location
//            a.placeAmount = 0
//            try {
//                a.placeAmount = row.getCell(3).toString().toInt()
//            } catch (_: Exception) {
//            }
//            a.computerAmount = 0
//            try {
//                a.computerAmount = row.getCell(5).toString().toInt()
//            } catch (_: Exception) {
//            }
//            auditoriumRepository.save(a)
//        }
    }

}