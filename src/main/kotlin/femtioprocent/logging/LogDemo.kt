package femtioprocent.logging

import femtioprocent.logging.MyLogger
import femtioprocent.sundry.Sundry

val lorem =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum urna elit, viverra in eros nec, accumsan fringilla leo." +
            " Aliquam dolor sapien, gravida eu lobortis ut, ornare eu odio. Donec aliquet pharetra ligula, eu dapibus dui egestas quis." +
            " Proin et fermentum nibh. Nulla ullamcorper rutrum sapien id tristique. Phasellus viverra facilisis augue, eget ullamcorper sapien lacinia quis." +
            " Integer ultricies, libero vel luctus sodales, ipsum sem maximus eros, quis mattis justo nisl vel risus." +
            " Vivamus mi diam, interdum a laoreet placerat, feugiat sit amet purus. Phasellus vulputate semper finibus." +
            " Integer maximus eleifend pharetra. Nulla congue eleifend finibus. Nunc posuere varius ornare." +
            " Maecenas pharetra auctor elit, non accumsan felis elementum id. Maecenas at rhoncus purus. Curabitur nec mi ac mi mollis pulvinar ut ut arcu."

val loremList = lorem.replace(".", "").replace(",", "").split(" ")
fun lorem(m: Int, n: Int): String = loremList.drop(m).take(n).joinToString(" ")


class LogDemo {

    fun main(arg: String) {
        demo(arg)
    }

    private fun demo(arg: String) {

        println("")
        MyLogger.logOut.level = ALL
        MyLogger.logOut.severe("This is severe")
        MyLogger.logOut.warning("This is warning")
        MyLogger.logOut.info("This is info")
        MyLogger.logOut.config("This is config")
        MyLogger.logOut.fine("This is fine")
        MyLogger.logOut.finer("This is finer")
        MyLogger.logOut.finest("This is finest")
        Sundry.milliSleep(100)
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            LogDemo().main(if (args.size > 0) args[0] else "d")
        }
    }
}
