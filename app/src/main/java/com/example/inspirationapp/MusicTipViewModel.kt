package com.example.inspirationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MusicTipViewModel : ViewModel() {
    private val _musicTips = MutableLiveData<List<MusicTip>>()
    val musicTips: LiveData<List<MusicTip>> = _musicTips

    init {
        loadMusicTips()
    }

    private fun loadMusicTips() {
        val tips = listOf(

                MusicTip(1, "Symphony No. 9", "Beethoven", "Discover Beethoven's masterpiece and its revolutionary final chorus.", R.drawable.beethoven_symphony),
                MusicTip(2, "Kind of Blue", "Miles Davis", "Dive into the world of Jazz with Miles Davis' iconic album.", R.drawable.miles_davis_kind_of_blue),
                MusicTip(3, "Bohemian Rhapsody", "Queen", "Experience the operatic rock masterpiece by Queen.", R.drawable.queen_bohemian),
                MusicTip(4, "Thriller", "Michael Jackson", "Get thrilled by Michael Jackson's best-selling album.", R.drawable.mj_thriller),
                MusicTip(5, "One More Time", "Daft Punk", "Feel the pulse of modern electronic music with Daft Punk.", R.drawable.daft_punk_one_more_time),
                MusicTip(6, "Blowin' in the Wind", "Bob Dylan", "Reflect on Bob Dylan's timeless folk anthem.", R.drawable.bob_dylan_blowin),
                MusicTip(7, "The Thrill Is Gone", "B.B. King", "Feel the blues with B.B. King's classic hit.", R.drawable.bb_king_thrill),
                MusicTip(8, "No Woman, No Cry", "Bob Marley", "Embrace the reggae spirit with Bob Marley.", R.drawable.bob_marley_no_woman),
                MusicTip(9, "Ring of Fire", "Johnny Cash", "Walk the line of country music with Johnny Cash.", R.drawable.johnny_cash_ring_of_fire),
                MusicTip(10, "Lose Yourself", "Eminem", "Get motivated with Eminem's powerful lyrics.", R.drawable.eminem_lose_yourself),
                MusicTip(11, "Nessun Dorma", "Pavarotti", "Experience the dramatic power of opera with Pavarotti.", R.drawable.pavarotti_nessun),
                MusicTip(12, "Enter Sandman", "Metallica", "Rock hard with Metallica's heavy metal anthem.", R.drawable.metallica_enter_sandman),
                MusicTip(13, "Respect", "Aretha Franklin", "Feel the soul with Aretha Franklin's empowering song.", R.drawable.aretha_respect),
                MusicTip(14, "Blitzkrieg Bop", "The Ramones", "Punk's not dead with The Ramones' classic.", R.drawable.ramones_blitzkrieg),
                MusicTip(15, "Wake Up", "Arcade Fire", "Discover the indie scene with Arcade Fire.", R.drawable.arcade_fire_wake_up),
                MusicTip(16, "Move On Up a Little Higher", "Mahalia Jackson", "Lift your spirits with Mahalia Jackson's gospel.", R.drawable.mahalia_jackson_move),
                MusicTip(17, "Waka Waka", "Shakira", "Feel the world beat with Shakira's global hit.", R.drawable.shakira_waka_waka),
                MusicTip(18, "Stayin' Alive", "Bee Gees", "Groove with the Bee Gees' disco classic.", R.drawable.beegees_stayin_alive),
                MusicTip(19, "Ain't No Sunshine", "Bill Withers", "Soothe your soul with Bill Withers.", R.drawable.bill_withers_sunshine),
                MusicTip(20, "Orinoco Flow", "Enya", "Unwind with Enya's serene New Age melody.", R.drawable.enya_orinoco),
                MusicTip(21, "Smells Like Teen Spirit", "Nirvana", "Dive into Nirvana's grunge revolution.", R.drawable.nirvana_teen_spirit),
                MusicTip(22, "Livin' La Vida Loca", "Ricky Martin", "Spice up life with Ricky Martin's Latin pop.", R.drawable.ricky_martin_vida_loca),
                MusicTip(23, "Dynamite", "BTS", "Catch the K-Pop wave with BTS.", R.drawable.bts_dynamite),
                MusicTip(24, "Time to Say Goodbye", "Bocelli and Brightman", "Feel the classical crossover with Bocelli and Brightman.", R.drawable.bocelli_brightman_goodbye),
                MusicTip(25, "Chameleon", "Herbie Hancock", "Jazz meets funk in Herbie Hancock's fusion.", R.drawable.hancock_chameleon),
                MusicTip(26, "Despacito", "Luis Fonsi", "Dance to the reggaeton rhythms of Luis Fonsi.", R.drawable.fonsi_despacito),
                MusicTip(27, "A Message to You Rudy", "The Specials", "Ska's charm with The Specials.", R.drawable.specials_rudy),
                MusicTip(28, "Music for Airports", "Brian Eno", "Explore ambient soundscapes with Brian Eno.", R.drawable.brian_eno_airports),
                MusicTip(29, "The Four Seasons", "Vivaldi", "Revel in the beauty of Vivaldi's composition.", R.drawable.vivaldi_four_seasons),
                MusicTip(30, "My Girl", "The Temptations", "End the month with the sweet soulful sounds of 'My Girl' by The Temptations.", R.drawable.my_girl_temptations)
            )
        _musicTips.postValue(tips)
    }
}