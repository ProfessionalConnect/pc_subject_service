package com.pro.subject.controller

import com.pro.subject.dto.*
import com.pro.subject.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
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

    @GetMapping("/ms/teams/{teamId}")
    fun getSubjectsByTeamId(
        @PathVariable teamId: Long,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 4) pageable: Pageable
    ): ResponseEntity<Page<SubjectResponse>> =
        ResponseEntity.ok(subjectService.getSubjectsByTeamId(teamId, pageable))

    @GetMapping("/ms/{subjectId}")
    fun getSubject(
        @PathVariable subjectId: Long,
    ): ResponseEntity<SubjectResponse> =
        ResponseEntity.ok(subjectService.getSubject(subjectId))

    @PostMapping("/ps")
    fun setSubject(
        @RequestBody subjectRequest: SubjectRequest,
        @RequestHeader(value = "uuid") uuid: String
    ): ResponseEntity<Void> {
        val subjectId = subjectService.setSubject(uuid, subjectRequest)
        return ResponseEntity.created(URI("/api/v1/subjects/${subjectId}")).build()
    }

    @GetMapping("/ps/{subjectId}/grades")
    fun getGradeBySubject(
        @PathVariable subjectId: Long
    ): ResponseEntity<List<GradeResponse>> =
        ResponseEntity.ok(subjectService.getGradeBySubject(subjectId))

    @PostMapping("/ss/register")
    fun setGrade(
        @RequestBody gradeRequest: GradeRequest,
        @RequestHeader(value = "uuid") uuid: String
    ): ResponseEntity<ExecResponse> =
        ResponseEntity.ok(subjectService.setGrade(uuid, gradeRequest))

    @GetMapping("/ss/{subjectId}/grades")
    fun getGradeBySubjectAndUUID(
        @RequestHeader(value = "uuid") uuid: String,
        @PathVariable subjectId: Long
    ): ResponseEntity<List<GradeResponse>> =
        ResponseEntity.ok(subjectService.getGradeBySubjectAndUUID(uuid, subjectId))

    @GetMapping("/ss/grades")
    fun getGradeByUUID(
        @RequestHeader(value = "uuid") uuid: String
    ): ResponseEntity<List<GradeResponse>> =
        ResponseEntity.ok(subjectService.getGradeByUUID(uuid))
}