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
<p align="center">
  <img src="https://github.com/user-attachments/assets/2b543549-cb4b-481e-a486-68c11de14e2a" alt="Screenshot 1" width="200" />
  <img src="https://github.com/user-attachments/assets/13e4eed7-a993-4184-b588-a0bcc24cf003" alt="Screenshot 2" width="200" />
  <img src="https://github.com/user-attachments/assets/4de68971-52f5-444e-a9db-2e5cdf87cf45" alt="Screenshot 3" width="200" />
  <img src="https://github.com/user-attachments/assets/68f23658-825d-4abd-945d-2bf329862efc" alt="Screenshot 4" width="200" />
</p>

---
## 🧩 Cara Menjalankan
1. Clone repository: git clone https://github.com/aulianrfz/joke-finder.git
2. Buka di Android Studio.
3. Jalankan aplikasi.
4. Ketik keyword pada kolom search dan tekan tombol Search.
5. Hasil jokes akan muncul dalam list RecyclerView.
