VIDEOCLIPUL DE PREZENTARE POATE FI ACCESAT PE ACEST LINK : https://youtu.be/YgHjgj6SzOM

# Aplicație Web pentru Gestionarea unui Restaurant

Această aplicație este o platformă completă pentru managementul digital al unui restaurant, proiectată să îmbunătățească eficiența operațională și să creeze o experiență interactivă pentru utilizatori. Aplicația a fost dezvoltată utilizând tehnologii moderne, combinând un backend robust cu o interfață prietenoasă și intuitivă.

---

## Descriere Generală

Aplicația permite gestionarea tuturor aspectelor legate de operarea unui restaurant:
- **Pentru angajați**: facilitează gestionarea comenzilor, rezervărilor, meniului și inventarului.
- **Pentru clienți**: oferă posibilitatea de a consulta meniul, de a plasa comenzi online și de a face rezervări rapid și eficient.
- **Pentru administratori**: integrează un panou de control avansat pentru monitorizarea și gestionarea activităților restaurantului.

Platforma este accesibilă atât de pe desktop, cât și de pe dispozitive mobile, fiind optimizată pentru o experiență fluidă.

---

## Funcționalități Cheie

### 1. **Autentificare și Gestionarea Rolurilor**
Aplicația include un sistem de autentificare bazat pe roluri, fiecare utilizator având acces la funcționalități diferite:
- **Clienți**:
  - Pot crea conturi pentru a plasa comenzi și a face rezervări.
  - Au acces la un istoric al comenzilor lor și pot urmări starea acestora.
- **Ospătari**:
  - Pot vizualiza și administra rezervările active.
  - Gestionarea comenzilor direct de la mese.
- **Bucătari**:
  - Văd o listă în timp real cu comenzile plasate, incluzând detalii despre preparatele necesare.
  - Actualizează starea comenzilor pentru a indica când sunt gata.
- **Administrator**:
  - Acces la toate funcționalitățile aplicației.
  - Poate modifica meniul, gestiona utilizatorii.

### 2. **Gestionarea Meniului**
- Meniul restaurantului este structurat pe categorii (antreuri, feluri principale, deserturi, băuturi).
- Fiecare produs include:
  - O descriere detaliată.
  - Ingrediente.
  - Poze și prețuri.
- Administratorul poate:
  - Adăuga noi produse.
  - Modifica sau șterge produsele existente.
  - Actualiza inventarul în timp real.

### 3. **Procesarea Comenzilor**
- Clienții pot:
  - Vizualiza meniul și adăuga produse în coșul de cumpărături.
  - Personaliza comenzile (ex. adăugarea de extra-opțiuni sau eliminarea anumitor ingrediente).
- Comenzile sunt transmise bucătăriei în timp real.

### 4. **Gestionarea Rezervărilor**
- Clienții pot selecta data, ora și numărul de persoane pentru rezervare.
- Sistemul verifică automat disponibilitatea meselor.
- Ospătarii pot gestiona rezervările direct din aplicație, confirmând sau modificând cererile.
- 
---

## Structura Aplicației

### Interfața pentru Clienți
- **Home Page**:
  - Prezentare a restaurantului, cu imagini și descrieri.
- **Meniu**:
  - Vizualizare produse, adăugare în coș.
- **Rezervări**:
  - Selectare date și ore disponibile.
- **Profil**:
  - Istoric comenzi și rezervări.

### Interfața pentru Angajați
- **Panou de Rezervări**:
  - Ospătarii pot vizualiza și gestiona rezervările dintr-un calendar.
- **Panou Comenzi**:
  - Bucătarii văd comenzile grupate pe mese, cu timp estimat de finalizare.
- **Gestionare Meniu**:
  - Angajații cu rol de manager pot actualiza meniul și inventarul.
  - 
---

## Tehnologii Utilizate
- **Backend**: Java, Spring Boot.
- **Frontend**: HTML5, CSS3, Bootstrap.
- **Baza de Date**: PostgreSQL, cu un model entitate-relație optimizat pentru gestionarea comenzilor și rezervărilor.
- **Securitate**: Autentificare prin JWT și măsuri avansate împotriva atacurilor cibernetice.
- 
---

