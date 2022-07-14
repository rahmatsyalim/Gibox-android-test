<div id="top"></div>



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/rahmatsyalim/Gibox-android-test">
    <img src="app/src/main/res/drawable/giboxdigital.png" alt="Logo" width="120" height="80">
  </a>

<h3 align="center">Gibox android test</h3>

</div>



<!-- ABOUT PROJECT -->

## About Project

Project ini dibuat untuk memenuhi salah satu tahap proses reqruitment karyawan oleh PT. Verita Informatika
yang nantinya jika sudah qulified akan dipekerjakan sebagai Android Developer di PT. Gibox Digital
Asia.

Output aplikasi dari project ini :

* Tampilan form halaman login
* Validasi input form
* Business logic untuk autentikasi login user
* Tampilan list user dengan load on scroll menggunakan pagination
* Animasi loading saat load on scroll
* Tombol 'Coba lagi' jika gagal load on scroll dengan message error yang tertangkap
* Pull to refresh untuk memuat ulang list konten
* Placeholder dengan text informasi jika data yang diminta empty atau belum tersedia
* Pesan error saat initial load data atau saat refresh
* Logout untuk menghapus sesi user



<p align="right">(<a href="#top">back to top</a>)</p>

<!-- BUILD WITH -->

## Build With

* Kotlin - Base language
* Coroutines - Asynchronous task
* Koin - Dependency Injection
* Retrofit 2 dan Okhttp - Http request ke Rest API
* Gson converter - konversi JSON ke java/kotlin object
* Glide - Image uri loader
* Paging 3 - Pager
* Design patter MVVM with clean architecture
* Remote data dari [https://reqres.in/](https://reqres.in/)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- APP USAGE -->

## App Usage

Untuk auntentikasi login user dapat menggunakan email: eve.holt@reqres.in atau bisa dicek di
[https://reqres.in/](https://reqres.in/) dan password terserah asal tidak kosong.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- KNOWN ISSUE -->

## Known Issue

Untuk kendala di project ini yang saya temui ada satu yaitu :
Ketika Pager sudah melakukan initial load data list user, load on scroll sukses berjalan.
Kemudian saat pull untuk refresh data menggunakan fungsi onrefresh() dari adapternya Pager 3,
Initial page load terjadi tapi next loadnya tidak berjalan on scroll 

  
<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

Rahmat Syalim - rahmatsyalim@gmail.com

Project
Link: [https://github.com/rahmatsyalim/Gibox-android-test](https://github.com/rahmatsyalim/Gibox-android-test)

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[linkedin-url]: https://linkedin.com/in/othneildrew

[product-screenshot]: images/screenshot.png
