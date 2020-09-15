package com.boss.poetrydb2.ui.state

sealed class StateEvent {
    class GetAuthorListEvent : StateEvent()
    class GetRandomPoemEvent : StateEvent()
}