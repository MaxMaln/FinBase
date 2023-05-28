package com.example.finbase

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityTeoryReadBinding

class TeoryRead : AppCompatActivity() {

    private lateinit var binding: ActivityTeoryReadBinding

    private lateinit var sharedpref: SharedPref

    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedpref = SharedPref(this)

        if(sharedpref.loadNightModeState())
        {
            setTheme(R.style.DarkTheme)
        }
        else
        {
            setTheme(R.style.DayTheme)
        }

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityTeoryReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val message: String? = intent.getStringExtra("teory")

        val myAssetManager = this.applicationContext?.assets
        val files = myAssetManager?.list("")

        if(message == files!![2])
        {
            binding.backbutton.visibility = View.GONE
        }
        if(message == files[files.count()-1])
        {
            binding.forwardbutton.visibility = View.GONE
        }

        if(message == "url")
        {
            val urllist: ArrayList<UrlClass> = ArrayList()
            urllist.add(UrlClass("www.asv.org.ru","Агентство по страхованию вкладов: "))
            urllist.add(UrlClass("www.cbr.ru","Центральный Банк России: "))
            urllist.add(UrlClass("www.consultant.ru","Cправочная правовой системы «КонсультантПлюс»: "))
            urllist.add(UrlClass("www.gks.ru","Федеральная служба государственной статистики: "))
            urllist.add(UrlClass("www.banki.ru","Финансовый маркетплейс «Банки.ру»: "))
            binding.listUrl.adapter =UrlAdapter(this, urllist)
            binding.listUrl.visibility = View.VISIBLE
            binding.pdfView.visibility = View.GONE
        }
        else if (message == "sov")
        {
            val sovlist: ArrayList<SovClass> = ArrayList()
            sovlist.add(SovClass("1","Тратьте меньше, чем зарабатываете","Умение ограничивать свои желания и откладывать деньги – ключевой навык в достижении финансовой свободы. Заведите себе правило каждый месяц откладывать не менее 10% своего дохода. " +
                    "\n\nДля чего это нужно? В будущем вы сможете создать на основе этого капитала пассивные источники дохода, пополнить резервный фонд или скопить средства на что-то дорогостоящее."))
            sovlist.add(SovClass("2","В первую очередь удовлетворяйте базовые потребности","Когда стоит вопрос между «отложить эту сумму на покупку квартиры» и «лишний раз поехать за границу», какой выбор сделаете вы? Здравый смысл подсказывает, что в первую очередь следует удовлетворить базовую потребность в жилье. И лишь затем можно пускаться во все тяжкие и тратить деньги на развлечения."))
            sovlist.add(SovClass("3","Не делайте спонтанных покупок","Здесь вполне уместна небольшая история реального человека: «Однажды я был в другом городе и наткнулся на огромный книжный рынок. Моя страсть к книгам слишком велика, чтобы я не застрял там на часок. И, как результат, я вышел оттуда с кипой книг. Мало того, что они затрудняли передвижение, так с тех пор я не прочитал ни одной из них. Сумма, конечно, была потрачена небольшая, но совершенно бессмысленно»." +
                    "\n\nСпособ избежать этого – планировать свои расходы. Каждый месяц составляйте свой бюджет. Выделите обязательные платежи (коммунальные услуги, транспорт, Интернет, различные абонементы и т.д.), средства на еду, развлечения и другие категории."))
            sovlist.add(SovClass("4","Учитывайте не только стоимость покупки, но и ее содержание","Принимая решение о покупке, учитывайте и скрытые траты, которые не входят в основную цену. Приобретение автомобиля означает траты на бензин, запчасти, страховку, техобслуживание и т.д. Путешествие за границу означает не только плату за готовый тур, но и прочие расходы на сувениры, дополнительные экскурсии и форс-мажоры. Учитывайте этот фактор и всегда планируйте бюджет на содержание."))
            sovlist.add(SovClass("5","Создавайте финансовые резервы","Любое государство или частное предприятие имеет резервные фонды в структуре бюджета. Их наличие обусловлено непредсказуемостью мира, в котором мы живем. Финансовые кризисы, потеря работы, внезапная болезнь, судебный иск от недоброжелателя – все эти «черные лебеди» могут моментально опустошить ваш кошелек. " +
                    "\n\nОбязательно создайте свой личный резервный фонд на случай непредвиденных обстоятельств."))
            sovlist.add(SovClass("6","Отслеживайте свои траты", "Понаблюдайте за своими тратами в течение месяца, и вы наверняка обнаружите удивительные закономерности. А еще – массу возможностей сэкономить." +
                    "\n\nВы можете просто собирать чеки и записывать суммы в блокнот или же воспользоваться одним из мобильных приложений для финансового менеджмента."))
            sovlist.add(SovClass("7","Учитесь экономить","Умение экономить – это не скряжничество, а осознанный подход к совершению покупок. Никогда не хватайтесь за первый попавшийся товар. Потратьте немного времени, чтобы изучить рынок и выбрать более выгодное предложение." +
                    "\n\nТоргуйтесь – это вам ничего не стоит, за исключением той выгоды, которую вы можете получить. Используйте дисконтные программы, кэшбек и вообще все, что поможет вам сэкономить какую-либо долю расходов. Только делайте все это без фанатизма."))
            sovlist.add(SovClass("8","Ищите источники пассивного дохода","Умный человек работает на деньги, у мудрого человека деньги работают на него. Приблизительно так можно описать парадигму пассивного дохода. Если вы все сделали правильно, старательно откладывали деньги, то со временем у вас должен накопиться небольшой капитал." +
                    "\n\nИщите возможности для инвестирования своих средств. Это может быть бизнес, ценные бумаги, депозит – все что угодно, что будет приносить деньги без вашего участия."))
            sovlist.add(SovClass("9","Диверсифицируйте риски","Храните деньги в разных банках, в разных валютах, используйте разные источники дохода. Это позволит сохранить деньги от инфляции, кризисов, воров и прочих неприятных неожиданностей."))
            sovlist.add((SovClass("10","Обеспечьте безопасность своих средств","Чем больше мы переходим на безналичные расчеты, тем в большей опасности наши деньги. Раньше люди носили с собой небольшую сумму в кошельке, остальное хранили в безопасном месте или в банке. Сейчас хакеры взламывают банковские карты тысячами и добираются сразу до крупных сумм. " +
                    "\n\nДержите часть денег наличными, а для карт и электронных кошельков придумывайте надежные пароли.")))
            binding.listSov.adapter =SovAdapter(this, sovlist)
            binding.pdfView.visibility = View.GONE
            binding.listSov.visibility = View.VISIBLE
        }
        else
        {
            path = message!!

            binding.pdfView
                .fromAsset(message)
                .enableSwipe(true)
                .load()

            binding.backbutton.setOnClickListener {
                    for (i in 2 until files!!.count())
                    {
                        if(path == files[i])
                        {
                            try {
                                binding.pdfView
                                    .fromAsset(files[i-1])
                                    .enableSwipe(true)
                                    .load()
                                path = files[i-1]
                                if(path == files[2])
                                {
                                    binding.backbutton.visibility = View.GONE
                                }
                            }

                            finally { }
                        }
                    }
                if(path == files[files.count()-2])
                {
                    binding.forwardbutton.visibility = View.VISIBLE
                }
            }
            binding.forwardbutton.setOnClickListener{
                for (i in 2 until files!!.count())
                {
                    if(path == files[i])
                    {
                        try {
                            binding.pdfView
                                .fromAsset(files[i+1])
                                .enableSwipe(true)
                                .load()

                            path = files[i+1]

                            if(path == files[files.count()-1])
                            {
                                binding.forwardbutton.visibility = View.GONE
                            }
                            break
                        }

                        finally { }
                    }
                }
                if(path == files[3])
                {
                    binding.backbutton.visibility = View.VISIBLE
                }
            }
        }
    }

}