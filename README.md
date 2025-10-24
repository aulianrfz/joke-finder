# JokeFinder App

Aplikasi Android berbasis MVVM Architecture yang menampilkan daftar Jokes dari Chuck Norris API.
Dibangun dengan Java Android Native, menggunakan RecyclerView, Search Box, dan ViewModel untuk pengelolaan data secara reaktif.

---
## 🎯 Fitur Utama
Search Jokes
- Gunakan search box untuk mencari jokes berdasarkan keyword.
- Tekan tombol “Search” untuk memanggil API https://api.chucknorris.io/jokes/search?query={query}.

---
## ⚙️ Arsitektur (MVVM Flow)
View (MainActivity) -> ViewModel (JokeViewModel) -> Repository (JokeRepository) -> Network (ApiService → ApiClient)

## 🚀 Gambaran Aplikasi

---
## 🧩 Cara Menjalankan
1. Clone repository: git clone https://github.com/aulianrfz/joke-finder.git
2. Buka di Android Studio.
3. Jalankan aplikasi.
4. Ketik keyword pada kolom search dan tekan tombol Search.
5. Hasil jokes akan muncul dalam list RecyclerView.
