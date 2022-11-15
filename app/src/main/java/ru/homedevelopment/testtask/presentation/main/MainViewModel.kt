package ru.homedevelopment.testtask.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.homedevelopment.testtask.App
import ru.homedevelopment.testtask.data.repo.ImageRepo
import ru.homedevelopment.testtask.data.repo.PhoneRepo
import javax.inject.Inject

class MainViewModel: ViewModel() {

    @Inject
    lateinit var imageRepo: ImageRepo

    @Inject
    lateinit var phoneRepo: PhoneRepo

    private val mImagesUrls: MutableStateFlow<List<String>>
    val imagesUrls: StateFlow<List<String>>

    init {
        App.appComponent.inject(this)

        mImagesUrls = MutableStateFlow(emptyList())
        imagesUrls = mImagesUrls.asStateFlow()
    }

    fun getImages()  = viewModelScope.launch {
        if (imageRepo.getUpdateDb()) {
            imageRepo.setImageUrls(images)
            imageRepo.setUpdateDb(false)
        }
        mImagesUrls.value = imageRepo.getImageUrls()
    }

    fun unAuthorization() {
        phoneRepo.deletePhone()
    }

    companion object {
        private val images = listOf(
            "https://img1.akspic.ru/attachments/crops/1/7/1/2/0/102171/102171-peyzash-nebo-vodohranilishhe-priroda-fjord-1920x1080.jpg",
            "https://catherineasquithgallery.com/uploads/posts/2021-02/1612678074_74-p-kartinka-fon-zelenii-lug-125.jpg",
            "https://fikiwiki.com/uploads/posts/2022-02/1645058097_26-fikiwiki-com-p-kartinki-s-khoroshim-kachestvom-30.jpg",
            "https://postila.ru/data/c8/e0/5a/ef/c8e05aefda9fa631f10424621924f0eee7f97e11d5b83e0199fc1e1982682287.jpg",
            "https://answit.com/wp-content/uploads/2017/01/full-hd.jpg",
            "https://get.wallhere.com/photo/sunlight-landscape-hill-nature-grass-sky-field-green-morning-farm-horizon-plateau-cloud-tree-flower-grassland-plant-pasture-agriculture-meadow-plantation-plain-lawn-2560x1600-px-prairie-crop-rural-area-grass-family-paddy-field-General-551245.jpg",
            "https://mediasole.ru/data/images/412/412388/23stunning-landscape-view-argentina.jpg",
            "https://mobimg.b-cdn.net/v3/fetch/fc/fc97db329bd4482025eaa1e3961dc80e.jpeg",
            "https://fikiwiki.com/uploads/posts/2022-02/1644855593_25-fikiwiki-com-p-kartinki-khd-kachestva-26.jpg",
            "https://mobimg.b-cdn.net/v3/fetch/f9/f9f2de5608f00f96a18f470547e52bef.jpeg",
            "https://mobimg.b-cdn.net/v3/fetch/d2/d278abc240bbb916917fc54ee64925d5.jpeg",
            "https://get.wallhere.com/photo/trees-landscape-lake-water-nature-reflection-sky-morning-national-park-wilderness-Bank-mount-scenery-nature-reserve-tree-autumn-leaf-mountain-watercourse-tarn-1920x1200-px-computer-wallpaper-mountainous-landforms-biome-mountain-range-moraine-larch-elevation-791386.jpg",
            "https://mota.ru/upload/resize/1920/1080/upload/wallpapers/source/2010/09/09/12/00/23067/mota_ru_0101410-13c.jpg",
            "https://get.wallhere.com/photo/1920x1080-px-mountain-nature-river-rock-645757.jpg",
            "https://bipbap.ru/wp-content/uploads/2017/04/16928DmervZwIjMnAqxGdL.jpg",
            "https://mobimg.b-cdn.net/v3/fetch/1e/1ef8491a8b1a55e954f0db1051b591ad.jpeg?w=1470&r=0.5625",
            "https://ya.clan.su/_ph/13/17159531.jpg?1664636018",
            "https://mobimg.b-cdn.net/v3/fetch/93/934e5caffebca39140a7382373f0c63c.jpeg",
            "https://mobimg.b-cdn.net/v3/fetch/b7/b76a766ef450b12d3f47b8d5dcd3b0bb.jpeg",
            "https://img.fonwall.ru/o/11/nebo_oblaka_solntse_derevya_les.jpg?route=mid&h=750",
            "https://img.fonwall.ru/o/15/tumannoe-ozero-tsvetyi-goryi-sneg.jpg?route=mid&h=750",
            "https://img2.akspic.ru/attachments/crops/9/8/8/1/3/131889/131889-metropoliya-gorod-bruklin-gorodskoj_pejzazh-gorodskoj_rajon-3840x2160.jpg",
            "https://fikiwiki.com/uploads/posts/2022-02/1644965596_10-fikiwiki-com-p-kartinki-priroda-na-zastavku-telefona-10.jpg",
            "https://klike.net/uploads/posts/2019-11/1574605248_9.jpg",
            "https://img1.akspic.ru/attachments/crops/9/9/8/2/22899/22899-zamok_nojshvanshtajn-peyzash-doroga-senokosnoye_ugodye-pejzazhi_gor-1920x1080.jpg",
            "https://www.desktopbackground.org/download/o/2014/07/09/790888_chrome-set-as-wallpapers-wallpapers-zone_7539x5031_h.jpg",
            "https://img3.akspic.ru/attachments/crops/9/1/6/2/12619/12619-skaly-vodoem-mestnost-poberezhe-rok-1920x1080.jpg",
            "https://img1.akspic.ru/attachments/crops/2/9/3/2/22392/22392-pejzazhi_gor-ozero_peho-nacionalnyj_park_torres_del_pajne-torres_del_pajne_v_chili-gora-1920x1080.jpg",
            "https://img.fonwall.ru/o/74/more-volna-solntse-priroda.jpg?route=mid&amp;h=750",
            "https://www.sunhome.ru/i/wallpapers/69/gornoe-ozero-s-chistoi-vodoi.orig.jpg",
        )
    }
}