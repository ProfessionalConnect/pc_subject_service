package com.pro.subject.domain

import javax.persistence.*

/**
 * Created by Minky on 2022-02-06
 */

@Entity
@Table(name = "grade_tbl")
class Grade(
    uuid: String,
    testCode: String,
    codeType: CodeType,
    resultType: ResultType,
    subject: Subject,
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_sequence_gen")
    @SequenceGenerator(name = "grade_sequence_gen", sequenceName = "grade_sequence")
    @Column(name = "grade_id", nullable = false)
    var id: Long? = null

    @Column(nullable = false)
    var uuid = uuid

    @Column(nullable = false)
    var testCode = testCode

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var codeType = codeType

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var resultType = resultType

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    var subject = subject

}