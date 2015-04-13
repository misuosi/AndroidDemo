/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mythos.demo.example.pagecache;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

	/**
	 * 提供可以访问的字符串的url
	 */
	public final static String[] imageUrls = new String[] {
			"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
			"https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
			"https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
			"https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
			"https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg",
			"https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
			"https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg",
			"https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg",
			"https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",
			"https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",
			"https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg",
			"https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg",
			"https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg",
			"https://lh5.googleusercontent.com/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s1024/Closed%252520Door.jpg",
			"https://lh3.googleusercontent.com/-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s1024/Colorado%252520River%252520Sunset.jpg",
			"https://lh3.googleusercontent.com/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s1024/Colors%252520of%252520Autumn.jpg",
			"https://lh4.googleusercontent.com/-GztnWEIiMz8/URqukVCU7bI/AAAAAAAAAbs/jo2Hjv6MZ6M/s1024/Countryside.jpg",
			"https://lh4.googleusercontent.com/-bEg9EZ9QoiM/URquklz3FGI/AAAAAAAAAbs/UUuv8Ac2BaE/s1024/Death%252520Valley%252520-%252520Dunes.jpg",
			"https://lh6.googleusercontent.com/-ijQJ8W68tEE/URqulGkvFEI/AAAAAAAAAbs/zPXvIwi_rFw/s1024/Delicate%252520Arch.jpg",
			"https://lh5.googleusercontent.com/-Oh8mMy2ieng/URqullDwehI/AAAAAAAAAbs/TbdeEfsaIZY/s1024/Despair.jpg",
			"https://lh5.googleusercontent.com/-gl0y4UiAOlk/URqumC_KjBI/AAAAAAAAAbs/PM1eT7dn4oo/s1024/Eagle%252520Fall%252520Sunrise.jpg",
			"https://lh3.googleusercontent.com/-hYYHd2_vXPQ/URqumtJa9eI/AAAAAAAAAbs/wAalXVkbSh0/s1024/Electric%252520Storm.jpg",
			"https://lh5.googleusercontent.com/-PyY_yiyjPTo/URqunUOhHFI/AAAAAAAAAbs/azZoULNuJXc/s1024/False%252520Kiva.jpg",
			"https://lh6.googleusercontent.com/-PYvLVdvXywk/URqunwd8hfI/AAAAAAAAAbs/qiMwgkFvf6I/s1024/Fitzgerald%252520Streaks.jpg",
			"https://lh4.googleusercontent.com/-KIR_UobIIqY/URquoCZ9SlI/AAAAAAAAAbs/Y4d4q8sXu4c/s1024/Foggy%252520Sunset.jpg",
			"https://lh6.googleusercontent.com/-9lzOk_OWZH0/URquoo4xYoI/AAAAAAAAAbs/AwgzHtNVCwU/s1024/Frantic.jpg",
			"https://lh3.googleusercontent.com/-0X3JNaKaz48/URqupH78wpI/AAAAAAAAAbs/lHXxu_zbH8s/s1024/Golden%252520Gate%252520Afternoon.jpg",
			"https://lh6.googleusercontent.com/-95sb5ag7ABc/URqupl95RDI/AAAAAAAAAbs/g73R20iVTRA/s1024/Golden%252520Gate%252520Fog.jpg",
			"https://lh3.googleusercontent.com/-JB9v6rtgHhk/URqup21F-zI/AAAAAAAAAbs/64Fb8qMZWXk/s1024/Golden%252520Grass.jpg",
			"https://lh4.googleusercontent.com/-EIBGfnuLtII/URquqVHwaRI/AAAAAAAAAbs/FA4McV2u8VE/s1024/Grand%252520Teton.jpg",
			"https://lh4.googleusercontent.com/-WoMxZvmN9nY/URquq1v2AoI/AAAAAAAAAbs/grj5uMhL6NA/s1024/Grass%252520Closeup.jpg",
			"https://lh3.googleusercontent.com/-6hZiEHXx64Q/URqurxvNdqI/AAAAAAAAAbs/kWMXM3o5OVI/s1024/Green%252520Grass.jpg",
			"https://lh5.googleusercontent.com/-6LVb9OXtQ60/URquteBFuKI/AAAAAAAAAbs/4F4kRgecwFs/s1024/Hanging%252520Leaf.jpg",
			"https://lh4.googleusercontent.com/-zAvf__52ONk/URqutT_IuxI/AAAAAAAAAbs/D_bcuc0thoU/s1024/Highway%2525201.jpg",
			"https://lh6.googleusercontent.com/-H4SrUg615rA/URquuL27fXI/AAAAAAAAAbs/4aEqJfiMsOU/s1024/Horseshoe%252520Bend%252520Sunset.jpg",
			"https://lh4.googleusercontent.com/-JhFi4fb_Pqw/URquuX-QXbI/AAAAAAAAAbs/IXpYUxuweYM/s1024/Horseshoe%252520Bend.jpg",
			"https://lh5.googleusercontent.com/-UGgssvFRJ7g/URquueyJzGI/AAAAAAAAAbs/yYIBlLT0toM/s1024/Into%252520the%252520Blue.jpg",
			"https://lh6.googleusercontent.com/-GHeImuHqJBE/URqu_FKfVLI/AAAAAAAAAbs/axuEJeqam7Q/s1024/Sailing%252520Stones.jpg",
			"https://lh3.googleusercontent.com/-hBbYZjTOwGc/URqu_ycpIrI/AAAAAAAAAbs/nAdJUXnGJYE/s1024/Seahorse.jpg",
			"https://lh3.googleusercontent.com/-Iwi6-i6IexY/URqvAYZHsVI/AAAAAAAAAbs/5ETWl4qXsFE/s1024/Shinjuku%252520Street.jpg",
			"https://lh6.googleusercontent.com/-amhnySTM_MY/URqvAlb5KoI/AAAAAAAAAbs/pFCFgzlKsn0/s1024/Sierra%252520Heavens.jpg",
			"https://lh5.googleusercontent.com/-dJgjepFrYSo/URqvBVJZrAI/AAAAAAAAAbs/v-F5QWpYO6s/s1024/Sierra%252520Sunset.jpg",
			"https://lh4.googleusercontent.com/-Z4zGiC5nWdc/URqvBdEwivI/AAAAAAAAAbs/ZRZR1VJ84QA/s1024/Sin%252520Lights.jpg",
			"https://lh4.googleusercontent.com/-_0cYiWW8ccY/URqvBz3iM4I/AAAAAAAAAbs/9N_Wq8MhLTY/s1024/Starry%252520Lake.jpg",
			"https://lh3.googleusercontent.com/-A9LMoRyuQUA/URqvCYx_JoI/AAAAAAAAAbs/s7sde1Bz9cI/s1024/Starry%252520Night.jpg",
			"https://lh3.googleusercontent.com/-KtLJ3k858eY/URqvC_2h_bI/AAAAAAAAAbs/zzEBImwDA_g/s1024/Stream.jpg",
			"https://lh5.googleusercontent.com/-dFB7Lad6RcA/URqvDUftwWI/AAAAAAAAAbs/BrhoUtXTN7o/s1024/Strip%252520Sunset.jpg",
			"https://lh5.googleusercontent.com/-at6apgFiN20/URqvDyffUZI/AAAAAAAAAbs/clABCx171bE/s1024/Sunset%252520Hills.jpg",
			"https://lh4.googleusercontent.com/-7-EHhtQthII/URqvEYTk4vI/AAAAAAAAAbs/QSJZoB3YjVg/s1024/Tenaya%252520Lake%2525202.jpg",
			"https://lh6.googleusercontent.com/-8MrjV_a-Pok/URqvFC5repI/AAAAAAAAAbs/9inKTg9fbCE/s1024/Tenaya%252520Lake.jpg",
			"https://lh5.googleusercontent.com/-B1HW-z4zwao/URqvFWYRwUI/AAAAAAAAAbs/8Peli53Bs8I/s1024/The%252520Cave%252520BW.jpg",
			"https://lh3.googleusercontent.com/-PO4E-xZKAnQ/URqvGRqjYkI/AAAAAAAAAbs/42nyADFsXag/s1024/The%252520Fisherman.jpg",
			"https://lh4.googleusercontent.com/-iLyZlzfdy7s/URqvG0YScdI/AAAAAAAAAbs/1J9eDKmkXtk/s1024/The%252520Night%252520is%252520Coming.jpg",
			"https://lh6.googleusercontent.com/-G-k7YkkUco0/URqvHhah6fI/AAAAAAAAAbs/_taQQG7t0vo/s1024/The%252520Road.jpg",
			"https://lh6.googleusercontent.com/-h-ALJt7kSus/URqvIThqYfI/AAAAAAAAAbs/ejiv35olWS8/s1024/Tokyo%252520Heights.jpg",
			"https://lh5.googleusercontent.com/-Hy9k-TbS7xg/URqvIjQMOxI/AAAAAAAAAbs/RSpmmOATSkg/s1024/Tokyo%252520Highway.jpg",
			"https://lh6.googleusercontent.com/-83oOvMb4OZs/URqvJL0T7lI/AAAAAAAAAbs/c5TECZ6RONM/s1024/Tokyo%252520Smog.jpg",
			"https://lh3.googleusercontent.com/-FB-jfgREEfI/URqvJI3EXAI/AAAAAAAAAbs/XfyweiRF4v8/s1024/Tufa%252520at%252520Night.jpg",
			"https://lh4.googleusercontent.com/-vngKD5Z1U8w/URqvJUCEgPI/AAAAAAAAAbs/ulxCMVcU6EU/s1024/Valley%252520Sunset.jpg",
			"https://lh6.googleusercontent.com/-DOz5I2E2oMQ/URqvKMND1kI/AAAAAAAAAbs/Iqf0IsInleo/s1024/Windmill%252520Sunrise.jpg",
			"https://lh5.googleusercontent.com/-biyiyWcJ9MU/URqvKculiAI/AAAAAAAAAbs/jyPsCplJOpE/s1024/Windmill.jpg",
			"https://lh4.googleusercontent.com/-PDT167_xRdA/URqvK36mLcI/AAAAAAAAAbs/oi2ik9QseMI/s1024/Windmills.jpg",
			"https://lh5.googleusercontent.com/-kI_QdYx7VlU/URqvLXCB6gI/AAAAAAAAAbs/N31vlZ6u89o/s1024/Yet%252520Another%252520Rockaway%252520Sunset.jpg",
			"https://lh4.googleusercontent.com/-e9NHZ5k5MSs/URqvMIBZjtI/AAAAAAAAAbs/1fV810rDNfQ/s1024/Yosemite%252520Tree.jpg", };

	public final static String[] baiduImageUrl = new String[] {
			"http://c.hiphotos.baidu.com/image/pic/item/0df431adcbef760949f1dc922cdda3cc7dd99ec0.jpg",
			"http://h.hiphotos.baidu.com/image/w%3D230/sign=8012217daf345982c58ae2913cf5310b/3b292df5e0fe9925abfedc5936a85edf8cb171cc.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/c2cec3fdfc03924506182e248494a4c27d1e2568.jpg",
			"http://a.hiphotos.baidu.com/image/w%3D230/sign=923f1e25b0de9c82a665fe8c5c8080d2/d53f8794a4c27d1ed27cc1d818d5ad6eddc43857.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ff43bb8e6e81800a19d84388.jpg",
			"file:///C:/Users/Administrator/Desktop/42a98226cffc1e17aac867904990f603738de918.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adbe598c124836acaf2edd9816.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ec5e2e87ac259759ee3c6ddba2.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa51f77d1eb78f8c5495ee7ba3.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/d1160924ab18972bf32483e6e5cd7b899e510afb.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/0df431adcbef760949f1dc922cdda3cc7dd99ec0.jpg",
			"http://h.hiphotos.baidu.com/image/w%3D230/sign=8012217daf345982c58ae2913cf5310b/3b292df5e0fe9925abfedc5936a85edf8cb171cc.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/c2cec3fdfc03924506182e248494a4c27d1e2568.jpg",
			"http://a.hiphotos.baidu.com/image/w%3D230/sign=923f1e25b0de9c82a665fe8c5c8080d2/d53f8794a4c27d1ed27cc1d818d5ad6eddc43857.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ff43bb8e6e81800a19d84388.jpg",
			"file:///C:/Users/Administrator/Desktop/42a98226cffc1e17aac867904990f603738de918.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adbe598c124836acaf2edd9816.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ec5e2e87ac259759ee3c6ddba2.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa51f77d1eb78f8c5495ee7ba3.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/d1160924ab18972bf32483e6e5cd7b899e510afb.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/0df431adcbef760949f1dc922cdda3cc7dd99ec0.jpg",
			"http://h.hiphotos.baidu.com/image/w%3D230/sign=8012217daf345982c58ae2913cf5310b/3b292df5e0fe9925abfedc5936a85edf8cb171cc.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/c2cec3fdfc03924506182e248494a4c27d1e2568.jpg",
			"http://a.hiphotos.baidu.com/image/w%3D230/sign=923f1e25b0de9c82a665fe8c5c8080d2/d53f8794a4c27d1ed27cc1d818d5ad6eddc43857.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/9358d109b3de9c82ff43bb8e6e81800a19d84388.jpg",
			"file:///C:/Users/Administrator/Desktop/42a98226cffc1e17aac867904990f603738de918.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/b3b7d0a20cf431adbe598c124836acaf2edd9816.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ec5e2e87ac259759ee3c6ddba2.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa51f77d1eb78f8c5495ee7ba3.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/d1160924ab18972bf32483e6e5cd7b899e510afb.jpg" };
	
	public final static String[] image360 = new String[]{
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg",
		"http://www.ledao.so/pics/2012/08/16/2012816158117883.jpg"
	};
}
