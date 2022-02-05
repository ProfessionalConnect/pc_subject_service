package com.pro.subject.domain

import javax.persistence.*

/**
 * Created by Minky on 2022-02-05
 */

@Entity
@Table(name = "subject_tbl")
class Subject(
    teamId: Long,
    uuid: String,
    title: String,
    description: String,
    correctCode: String,
    codeType: CodeType,
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_sequence_gen")
    @SequenceGenerator(name = "subject_sequence_gen", sequenceName = "subject_sequence")
    @Column(name = "subject_id", nullable = false)
    var id: Long? = null

    @Column(nullable = false)
    var teamId = teamId

    @Column(nullable = false)
    var uuid = uuid

    @Column(nullable = false)
    var title = title

    @Column(nullable = false)
    var description = description

    @Column(nullable = false)
    var correctCode = correctCode

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var codeType = codeType

    @OneToMany(mappedBy = "subject", cascade = [(CascadeType.PERSIST)], orphanRemoval = true)
    var testCaseList = mutableListOf<TestCase>()
}