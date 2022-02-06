package com.pro.subject.controller

import com.pro.subject.dto.ExecResponse
import com.pro.subject.dto.GradeRequest
import com.pro.subject.dto.SubjectRequest
import com.pro.subject.dto.SubjectResponse
import com.pro.subject.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * Created by Minky on 2022-02-05
 */

@RestController
@RequestMapping("/api/v1/subjects")
class SubjectController {
    @Autowired
    private lateinit var subjectService: SubjectService

    @GetMapping("/{teamId}")
    fun getSubjectsByTeamId(@PathVariable teamId: Long): ResponseEntity<List<SubjectResponse>> =
        ResponseEntity.ok(subjectService.getSubjectsByTeamId(teamId))

    @PostMapping
    fun setSubject(@RequestBody subjectRequest: SubjectRequest): ResponseEntity<Void> {
        val subjectId = subjectService.setSubject(subjectRequest)
        return ResponseEntity.created(URI("/api/v1/subjects/${subjectId}")).build()
    }

    @PostMapping("/register")
    fun setGrade(@RequestBody gradeRequest: GradeRequest): ResponseEntity<ExecResponse> =
        ResponseEntity.ok(subjectService.setGrade(gradeRequest))
}