package net.cthayes.ical

data class Event(
    var title:String = "",
    var description:String = "",
    var location:String = "",
    var start:String = "",
    var end:String = ""
)