package net.cthayes.ical

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*

class Calendar {
   suspend fun loadData(url:String):List<Event> {
       val client = HttpClient()
       val response: HttpResponse = client.get(url)
       client.close()
       val text = response.readText()
       return parseData(text)
    }

    fun getValue(line:String):String {
        val index = line.indexOf(":")
        val value = line.substring(index+1, line.length-1)
        return value
    }

    fun normalizeDateToUTC(date:String):String {
        // 20210620T143000Z
        if (date.takeLast(1) == "Z") {
            return date
        }

        // Assume EST time
        // 20210612T110000
        val tIndex = date.lastIndexOf("T")
        val partialDate = date.substring(0, tIndex)
        val time = date.substring(tIndex+1)
        val timeInt = time.toInt()
        val newTimeInt = timeInt + 40000
        return "${partialDate}T${newTimeInt.toString()}Z"
    }

    fun parseData(data:String):List<Event> {
        val lines:List<String> = data.split("\n")

        var currentEvent:Event? = null
        val events = mutableListOf<Event>()

        for (line in lines) {
            if (line.startsWith("BEGIN:VEVENT")) {
                currentEvent = Event()
            }

            if (line.startsWith("SUMMARY")) {
                currentEvent?.title = getValue(line)
            }

            if (line.startsWith("DESCRIPTION")) {
                currentEvent?.description = getValue(line)
            }

            if (line.startsWith("LOCATION")) {
                currentEvent?.location = getValue(line)
            }

            if (line.startsWith("DTSTART")) {
                currentEvent?.start = normalizeDateToUTC(getValue(line))
            }

            if (line.startsWith("DTEND")) {
                currentEvent?.end = normalizeDateToUTC(getValue(line))
            }

            if (line.startsWith("END:VEVENT")) {
                if (currentEvent != null) {
                    events.add(currentEvent)
                    currentEvent = null
                }
            }
        }

        return events
    }
}