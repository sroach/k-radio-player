package gy.roach.kradioplayer

/**
 * Radio station model matching the Rust shared model.
 */
data class RadioStation(
    val index: Int,
    val url: String,
    val label: String,
    val type: List<String>,
    val imageResource: String? = null
)

/**
 * Returns the list of Caribbean radio stations.
 */
fun getStations(): List<RadioStation> = listOf(
    RadioStation(0, "https://streaming.radio.co/sca6c5aded/listen", "Pepperpot Radio", listOf("Reggae", "Soca", "Calypso")),
    RadioStation(1, "http://n02.radiojar.com/nh42m158d4duv?_=1&rj-tok=AAABfl9pxJ0Ar0s9I0FDPohs9g&rj-ttl=5", "Guyana Lite 104.1 FM", listOf("Oldies", "News")),
    RadioStation(2, "http://192.99.8.192:3630/stream/;", "HJ 94.1 Boom FM", listOf("Adult Contemporary", "Hits", "Hip Hop", "R&B")),
    RadioStation(3, "https://auds1.intacs.com/thecaribbeanradioindo", "Bollywood Caribbean Radio Station", listOf("Bollywood")),
    RadioStation(4, "https://auds1.intacs.com/thecaribbeanradio", "The Caribbean Radio Station", listOf("Dancehall", "Soca", "Reggae")),
    RadioStation(5, "https://stream.radio.co/s9668e2ab5/listen", "Radio Guyana Inc. live", listOf("Bollywood", "Chutney", "Reggae", "Soca")),
    RadioStation(6, "http://abee.radioca.st/stream", "Abee Radio", listOf("Bollywood", "Chutney", "Indian", "Reggae", "Soca")),
    RadioStation(7, "http://streaming.broadcastradio.com:8870/maad975fm", "MAAD 97.5 FM", listOf("Dancehall", "Reggae", "World")),
    RadioStation(8, "https://s4.radio.co/s75af666f2/listen", "96.1 VOICE FM", listOf("Reggae", "Soca")),
    RadioStation(9, "https://audio-edge-qse4n.yyz.g.radiomast.io/aa758625-6c37-4a31-8262-b2bf84bbe17f", "Mix FM", listOf("Reggae", "Soca")),
    RadioStation(10, "https://halo.streamerr.co/radio/8030/radio.mp3?refresh=1649376724995", "Radio Guyana International", listOf("Adult Contemporary", "Pop", "Top 40")),
    RadioStation(11, "https://stream-162.zeno.fm/m19sfp6wxd0uv?zs=vUwfSKd4T9GTg1IUkgsh4Q", "Basil P Radio", listOf("Soul", "R&B", "Oldies", "Disco", "Reggae", "Soca")),
    RadioStation(12, "https://stream-153.zeno.fm/tdngaa9tszzuv?zs=c_CGWREdQIG2ht1QSVmUwA", "Radio592FM", listOf("Bollywood", "Oldies")),
    RadioStation(13, "https://icecast.walmradio.com:8443/classic", "Classic", listOf("big band", "classic hits")),
    RadioStation(14, "https://cast4.asurahosting.com/proxy/ncn1001/stream", "Vybz 100.1 FM live", listOf("Reggae", "Oldies")),
    RadioStation(15, "http://185.80.220.12:8046/stream?cb=307101.mp3", "Bollywood Radio & Beyond", listOf("Bollywood")),
    RadioStation(16, "http://www.desizone.nl:8000/listen.pls?sid=1&t=.pls", "Desi Zone", listOf("Bollywood")),
    RadioStation(17, "http://109.169.46.197:8011/listen.pls?sid=1&t=.pls", "Diverse FM", listOf("Bollywood")),
    RadioStation(18, "http://cast2.asurahosting.com:8569/stream?DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY", "It's Hot Mirchi 102.4 FM", listOf("Bollywood")),
    RadioStation(19, "https://eu8.fastcast4u.com/proxy/clyedupq?mp=/1&DIST=TuneIn&TGT=TuneIn&maxServers=2&gdpr=0&us_privacy=1YNY", "Hindi Desi Bollywood", listOf("Bollywood")),
    RadioStation(20, "http://auds1.intacs.com/ntnradio", "NTN Radio 89.1 Georgetown", listOf("Bollywood")),
    RadioStation(21, "https://www.radiomast.io/stream/68540e88-54b8-4924-b098-5a668f7da7b6/listen", "Slam 100.5", listOf("Urban")),
    RadioStation(22, "https://mangoradio.stream.laut.fm/mangoradio", "Mango Radio", listOf("Urban")),
    RadioStation(23, "http://www.101smoothjazz.com/101-smoothjazz.m3u", "101 Smooth Jazz", listOf("Jazz")),
    RadioStation(24, "https://stream.zeno.fm/6n6ewddtad0uv", "Bollywood Gaane Purane", listOf("Bollywood")),
    RadioStation(25, "https://stream.zeno.fm/rm4i9pdex3cuv", "RADIO BOLLYWOOD 90s", listOf("Bollywood")),
    RadioStation(26, "https://eu8.fastcast4u.com/proxy/clyedupq/stream", "Radio Mirchi Hindi", listOf("Bollywood")),
    RadioStation(27, "http://playerservices.streamtheworld.com/pls/977_HITS.pls", ".977 Hits", listOf("Urban")),
    RadioStation(28, "https://stream.zeno.fm/dbstwo3dvhhtv", "92.7 Big FM online", listOf("Bollywood", "Pop")),
    RadioStation(29, "https://stream-174.zeno.fm/q97eczydqrhvv?zt=eyJhbGciOiJIUzI1NiJ9", "Red FM 93.5", listOf("Bollywood", "Pop"))
)
