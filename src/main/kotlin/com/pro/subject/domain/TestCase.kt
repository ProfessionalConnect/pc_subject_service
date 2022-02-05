package com.pro.subject.domain

import javax.persistence.*

/**
 * Created by Minky on 2022-02-06
 */

@Entity
@Table(name = "testcase_tbl")
class TestCase(
    subject: Subject,
    testArgument: String,
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testcase_sequence_gen")
    @SequenceGenerator(name = "testcase_sequence_gen", sequenceName = "testcase_sequence")
    @Column(name = "testcase_id", nullable = false)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    var subject = subject

    @Column(nullable = false)
    var testArgument = testArgument
}