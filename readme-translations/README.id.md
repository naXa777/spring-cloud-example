# Contoh Spring Cloud 

[![Status Build](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example)

Playground ini dipergunakan untuk pembelajaran [Spring Cloud](https://cloud.spring.io/) dan [Kotlin](https://kotlinlang.org/) berlandaskan contoh-contoh.

Baca ini dalam bahasa lain:
* [На русском](../README.md)
* [In English](README.en.md)
* [In het Nederlands](README.nl.md)

## Kontribusi

&#x1F49A; Mulai [disini](https://github.com/naXa777/spring-cloud-example/issues).

## Deskripsi

> [Cloud-native](https://pivotal.io/de/cloud-native) merupakan pendekatan yang digunakan untuk membangun dan menjalankan aplikasi yang memanfaatkan keuntungan dari model komputasi awan.

> [Microservices](https://pivotal.io/microservices) merupakan pendekatan yang digunakan untuk mengembangkan sebuah aplikasi sebagai suatu koleksi dari servis-servis kecil; setiap servis ini mengimplementasikan kapabilitas bisnis, jalan dalam proses tersendiri dan berkomunikasi melalui API HTTP. Setiap microservice dapat dideploy, upgrade, scale, dan restart namun tidak bergantung pada servis lain pada aplikasi tersebut, yang biasanya merupakan bagian dari sistem otomata.

[(c) Pivotal](https://pivotal.io/de/cloud-native)

Kita membagi aplikasi besar menjadi bagian-bagian yang lebih kecil yang disebut 'services'. Dan setiap dari servis ini dapat dideploy atau scale secara tersendiri. _Bagaimana suatu servis mengetahui yang lainnya?_

### Penemuan servis

|      Modul       |           URL             |
| :--------------: | :-----------------------: |
| discovery-server | `http://host:8761/eureka` |

Terdapat beberapa cara Anda dapat menemukan servis di Spring Cloud:

* Spring Cloud [Consul](https://cloud.spring.io/spring-cloud-consul/)
* Spring Cloud [Zookeeper](https://cloud.spring.io/spring-cloud-zookeeper/)
* Spring Cloud [Netflix](https://cloud.spring.io/spring-cloud-netflix/)

Projek ini secara spesifik fokus ke poin terakhir, yaitu projek Netflix Spring Cloud. Media service streaming berbasis awan yang dimiliki oleh Amerika Serikat, yang dinamakan Netflix ini, telah membuka konten framework dan alat-alat microservicenya.

Demi kesederhanaan, terdapat satu instansi server Eureka. Maka server Eureka tidak dikonfigurasi untuk mendaftarkan dirinya sendiri dengan peer yang lain.
Anda mungkin ingin memiliki banyak server discovery demi ketersediaan saat produksi, maka ubah properti berikut:

    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true

#### Ketersediaan Tinggi 

Eureka memastikan secara periodik bahwa servis aplikasi yang di return atau dikembalikan ke client dalam kondisi baik dan available. Dan ini juga memastikan bahwa dalam waktu tertentu ketika Server Discovery nya down, semua client masih dapat berjalan. 

Eureka dibangun dengan pemikiran ketersediaan yang tinggi:

 * Registrynya terdistribusi (local cache pada setiap client).
 * Client  _dapat_ beroperasi tanpa server discovery (apabila servernya down).
 * Client mengambil deltas untuk memperbarui registry.

#### Dashboard

Dashbord Eureka berbasis web teraktifkan sebagai default. Ini menunjukkan informasi-informasi penting seperti metadata servis.
 
Dalam environment local ini terdapat di http://localhost:8761

### Layanan Cuaca 

| Modul           | URL                        |
| :-------------: | :------------------------: |
| weather-service | `http://host:port/weather` |

Anotasi `@EnableDiscoveryClient` digunakan untuk menjadikan
`WeatherServiceApplication` menjadi client sebuah Discovery Server. Ini
menyebabkan client tersebut terdaftar dengan Discovery Server saat
dijalankan. 

### Weather App

| Modul       | URL                                |
| :---------: | :--------------------------------: |
| weather-app | `http://host:port/current/weather` |

### Layanan Datetime 

| Modul            | URL                         |
| :--------------: | :-------------------------: |
| datetime-service | `http://host:port/datetime` |

Anotasi `@EnableDiscoveryClient` digunakan untuk menjadikan `DatetimeServiceApplication` menjadi client sebuah Discovery Server. Ini menyebabkan client tersebut terdaftar dengan Discovery Server saat dijalankan.

### Datetime App

| Modul        | URL                                 |
| :----------: | :---------------------------------: |
| datetime-app | `http://host:port/current/datetime` |

### Client

| Modul   | URL                |
| :-----: | :----------------: |
| client  | `http://host:port` |

Anotasi `@EnableDiscoveryClient` digunakan untuk menjadikan `ClientApplication` menjadi suatu client Discovery Server. 

Client ini tidak perlu mendaftar dengan Eureka karena client ini tidak ingin
siapapun untuk menemukannya sehingga properti berikut di set ke false:

    eureka.client.register-with-eureka=false
    
### Toleransi Kesalahan

Dalam sistem terdistribusi, terdapat suatu hal yang pasti... KESALAHAN TIDAK
DAPAT DIHINDARI.

Sebuah efek yang buruk dari kesalahan tersebut dalam sistem terdistribusi adalah [cascading failure](https://en.wikipedia.org/wiki/Cascading_failure). Dari Wikipedia Bahasa Inggris:

> It is a process in a system of interconnected parts in which the failure of one or few parts can trigger the failure of other parts and so on.

Bagaimana cara menghadapi kegagalan atau kesalahan?

 * Toleransi kesalahan
 * Degradasi anggun
 * Membatasi sumber daya 

> [Circuit breaker design pattern](https://en.wikipedia.org/wiki/Circuit_breaker_design_pattern) adalah design pattern yang digunakan dalam pengembangan perangkat lunak modern. Ini digunakan untuk mendeteksi kesalahan dan mengenkapsulasikan sedemikian rupa sehingga menghindari kesalahan tersebut untuk timbul berkali-kali nantinya.

(Dari Wikipedia)

#### Hystrix

[Netflix Hystrix](https://github.com/Netflix/Hystrix) merupakan library latensi dan **toleransi kesalahan** yang didesain untuk mengisolasi titik akses ke suatu remote system, servis, dan library pihak ketiga, **menghentikan kegagalan beruntun** dan memungkinkan ketahanan dari sistem terdistribusi yang rumit dimana kegagalan atau kesalahan tidak dapat dihindari.

Lihat [Cara kerjanya](https://github.com/Netflix/Hystrix/wiki/How-it-Works).

#### Hystrix Dashboard

| Modul              | URL                        |
| :----------------: | :------------------------: |
| hystrix-dashboard  | `http://host:port/hystrix` |

Dokumentasi: [Circuit Breaker: Hystrix Dashboard](https://cloud.spring.io/spring-cloud-static/Edgware.SR4/multi/multi__circuit_breaker_hystrix_dashboard.html)

#### Turbine

| Modul    | URL                               |
| :------: | :-------------------------------: |
| turbine  | `http://host:3000/turbine.stream` |

Dashboard Hystrix hanya dapat memantau satu microservice dalam momen tersebut. Apabila terdapat banyak microservis, maka dashboard Hystrix harus diarahkan setiap kali saat ingin memantau microservis lain. Ini cukup repot.

Turbine (disediakan oleh projek Spring Cloud Netflix), mengumpulkan beberapa instansi dari Hystrix metrics streams, jadi dashboard tersebut dapat menampilkan tampilan agregat atau tampilan kumpulan.

Contoh config:

    turbine.app-config=weather-app,datetime-app
    turbine.cluster-name-expression='default'

## Membangun dan menjalankan secara lokal

### Permulaan 

Sebelum build pertama, Anda harus melakukan langkah-langkah tambahan berikut.

 - Pertama, clone repository.
 - Kedua, definisikan beberapa variabel environment.
   - &#x1F4D7; _TODO_

Anda memiliki kebebasan untuk memilih build tools untuk projek ini: [Gradle](https://gradle.org/) atau IDE favorit Anda.
[IntelliJ IDEA](https://spring.io/guides/gs/intellij-idea/), [STS](https://stackoverflow.com/q/34214685/1429387) / [Eclipse](http://www.vogella.com/tutorials/EclipseGradle/article.html), atau [NetBeans](https://netbeans.org/features/java/build-tools.html) harusnya dapat mengatasi pekerjaan ini dengan lancar.

### Menggunakan Gradle Wrapper

    ./gradlew :discovery-server:bootRun
    ./gradlew :weather-service:bootRun
    ./gradlew :weather-app:bootRun
    ./gradlew :client:bootRun
    ./gradlew :datetime-service:bootRun
    ./gradlew :datetime-app:bootRun
    ./gradlew :turbine:bootRun
    ./gradlew :hystrix-dashboard:bootRun

### Menggunakan IntelliJ IDEA

1. Import root project di IntelliJ IDEA
2. Sync file project dengan Gradle (sync awal mungkin dapat terjadi secara
	 otomatis) 
3. Sekarang Anda harusnya sudah punya beberapa run configurations untuk setiap modul. Jalankan konfigurasi tersebut satu per satu:
    1. DiscoveryServerApplication
    2. WeatherServiceApplication
    3. WeatherAppApplication
    4. ClientApplication
    5. DatetimeServiceApplication
    6. DatetimeAppApplication
    7. TurbineApplication
    8. HystrixDashboardApplication

Tips: pastikan bahwa Anda menjalankannya di port yang berbeda dan port tersebut tidak digunakan oleh yang lain, jika tidak makan Anda akan mendapatkan error.

## Deploy ke Cloud

&#x1F4D7; _TODO_

## Continuous Integration

| [Travis CI](https://travis-ci.com/) | [![Build Status](https://travis-ci.com/naXa777/spring-cloud-example.svg?branch=master&style=flat)](https://travis-ci.com/naXa777/spring-cloud-example) |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |

## License

Projek ini terlisensi dengan ketentuan lisensi [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html).
