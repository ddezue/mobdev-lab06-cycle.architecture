# Лабораторная работа №6: Жизненный цикл. Архитектурные компоненты

## Цель работы
Изучить жизненный цикл приложения, архитектурный паттерн MVVM и возможности навигации в Jetpack Compose.

## Выполненные задания

### Практическая часть: Приложение Unscramble
Приложение-игра "Расшифруй слово" переведено на архитектуру MVVM с однонаправленным потоком данных (UDF).
* Реализован класс состояния `GameUiState`.
* Создан `GameViewModel` с использованием `MutableStateFlow` для хранения состояния и `StateFlow` (через `asStateFlow()`) для его чтения.
* В `GameScreen` состояние собирается через `collectAsState()`. Логика проверки слов, начисления очков, пропуска и завершения игры перенесена во ViewModel.

### Задание 1: Приложение ActivityLifecycle
Создано приложение для отслеживания жизненного цикла Activity. В `MainActivity` переопределены методы `onCreate`, `onStart`, `onResume`, `onPause`, `onStop`, `onRestart`, `onDestroy`. Вызовы методов логируются в Logcat с тегом `LifecycleTest`.
<img width="1251" height="866" alt="image" src="https://github.com/user-attachments/assets/8c6b8e31-426e-46d9-8c80-265fe2ff79c3" />

**Выводы по жизненному циклу:**
1. **Запуск приложения:** Вызывается последовательность `onCreate` -> `onStart` -> `onResume`.
2. **Сворачивание и разворачивание (Home):** При сворачивании вызываются `onPause` -> `onStop`. При возврате в приложение: `onRestart` -> `onStart` -> `onResume`.
3. **Смена ориентации (поворот):** Activity полностью уничтожается и создается заново: `onPause` -> `onStop` -> `onDestroy` -> `onCreate` -> `onStart` -> `onResume`. Для сохранения данных при повороте необходимо использовать ViewModel или `rememberSaveable`.

### Задание 2: Доработка ArtSpaceApp (Добавление ViewModel)
Приложение-галерея из ЛР №5 доработано с применением ViewModel.
* Создан класс состояния `ArtSpaceUiState`, инкапсулирующий индекс текущей картинки.
* Создан `ArtSpaceViewModel`. Состояние хранится в приватной переменной `MutableStateFlow` и доступно только для чтения через `StateFlow` с помощью `asStateFlow()`.
* Логика переключения картинок вынесена из UI во ViewModel.
<img width="824" height="191" alt="image" src="https://github.com/user-attachments/assets/89de01f4-7996-4207-b214-6e117cd1b1b8" />
<img width="367" height="761" alt="image" src="https://github.com/user-attachments/assets/aac1d77b-52f4-4d84-af9a-d9c7b26b05b6" />
<img width="891" height="432" alt="image" src="https://github.com/user-attachments/assets/8fd8d965-a379-4d4f-a0e8-b79658e2c85f" />

### Задание 3: Доработка Dessert Clicker (Добавление ViewModel)
Стартовый проект Dessert Clicker доработан по паттерну MVVM.
* Создан класс состояния `DessertUiState` (хранит выручку, количество проданных десертов, текущую картинку и цену).
* Создан `DessertViewModel`, в который перенесена бизнес-логика (функция `determineDessertToShow` и обработка клика).
* UI подписан на состояние ViewModel через `collectAsState()`.
<img width="701" height="379" alt="image" src="https://github.com/user-attachments/assets/3200bc85-780e-4685-a501-94054eda80d6" />
<img width="288" height="570" alt="image" src="https://github.com/user-attachments/assets/32da32a4-c21e-4f95-b777-9f83c73df15b" />


### Задание 4: Тестирование ViewModel (Unscramble)
Написаны Unit-тесты для проверки логики `GameViewModel`.
* `gameViewModel_IncorrectGuess_ErrorFlagSet` — проверка установки флага ошибки при неверном вводе.
* `gameViewModel_Initialization_FirstWordLoaded` — проверка корректной инициализации первого слова и начального состояния.
* `gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly` — проверка правильного начисления очков и завершения игры после прохождения всех слов.
* `gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased` — проверка того, что при пропуске слова счет не меняется, а счетчик слов увеличивается.
<img width="661" height="315" alt="image" src="https://github.com/user-attachments/assets/06046261-1aae-424b-b6d3-666a06761c2b" />
![Uploading image.png…]()



**Результат покрытия:** Покрытие классов и методов `GameViewModel` составляет 100%. Покрытие строк — 97%. Показатель ниже 100% обусловлен наличием цикла в функции перемешивания слов, который зависит от генератора случайных чисел (Random) и не может быть гарантированно вызван в тестах.
