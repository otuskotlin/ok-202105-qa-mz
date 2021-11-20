package ru.otus.opinion.repo.cassandra.dto

import ru.otus.opinion.models.Permission
import ru.otus.opinion.models.QuestionState
import ru.otus.opinion.models.QuestionVisibility

object ModelDtoMapper {

    internal fun toDto(permission: Permission): PermissionDto = when (permission) {
        Permission.READ -> PermissionDto.READ
        Permission.UPDATE -> PermissionDto.UPDATE
        Permission.DELETE -> PermissionDto.DELETE
    }

    internal fun toDto(state: QuestionState) : QuestionStateDto = when(state) {
        QuestionState.PROPOSED -> QuestionStateDto.PROPOSED
        QuestionState.MODERATED -> QuestionStateDto.MODERATED
        QuestionState.ACCEPTED -> QuestionStateDto.ACCEPTED
        QuestionState.OPENED -> QuestionStateDto.OPENED
        QuestionState.CLOSED -> QuestionStateDto.CLOSED
    }

    internal fun toDto(visibility: QuestionVisibility) : QuestionVisibilityDto = when(visibility) {
        QuestionVisibility.OWNER_ONLY -> QuestionVisibilityDto.OWNER_ONLY
        QuestionVisibility.REGISTERED_ONLY -> QuestionVisibilityDto.REGISTERED_ONLY
        QuestionVisibility.PUBLIC -> QuestionVisibilityDto.PUBLIC
    }

    internal fun toModel(permission: PermissionDto): Permission = when (permission) {
        PermissionDto.READ -> Permission.READ
        PermissionDto.UPDATE -> Permission.UPDATE
        PermissionDto.DELETE -> Permission.DELETE
    }

    internal fun toModel(state: QuestionStateDto) : QuestionState = when(state) {
        QuestionStateDto.PROPOSED -> QuestionState.PROPOSED
        QuestionStateDto.MODERATED -> QuestionState.MODERATED
        QuestionStateDto.ACCEPTED -> QuestionState.ACCEPTED
        QuestionStateDto.OPENED -> QuestionState.OPENED
        QuestionStateDto.CLOSED -> QuestionState.CLOSED
    }

    internal fun toModel(visibility: QuestionVisibilityDto) : QuestionVisibility = when(visibility) {
        QuestionVisibilityDto.OWNER_ONLY -> QuestionVisibility.OWNER_ONLY
        QuestionVisibilityDto.REGISTERED_ONLY -> QuestionVisibility.REGISTERED_ONLY
        QuestionVisibilityDto.PUBLIC -> QuestionVisibility.PUBLIC
    }
}