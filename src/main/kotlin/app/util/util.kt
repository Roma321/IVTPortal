package app.util

import app.models.Teacher

fun getTeacherFullName(it: Teacher) = "${it.lastName} ${it.firstName} ${it.patronymic}"