# Задания 1 (Решение)

#### 1. Используйте команды операционной системы Linux и создайте две новых директории – «Игрушки для школьников» и «Игрушки для дошколят»

   ```
   cd && mkdir -p "Игрушки для школьников" "Игрушки для дошколят"
   ```

#### 2. Создайте в директории «Игрушки для школьников» текстовые файлы - «Роботы», «Конструктор», «Настольные игры»

   ```
   cd "Игрушки для школьников" && touch Роботы.txt Конструктор.txt Настольные\ игры.txt && cd
   ```

#### 3. Создайте в директории «Игрушки для дошколят» текстовые файлы «Мягкие игрушки», «Куклы», «Машинки»

   ```
   cd "Игрушки для дошколят" && touch Мягкие\ игрушки.txt Куклы.txt Машинки.txt && cd
   ```

#### 4. Объединить 2 директории в одну «Имя Игрушки»

   ```
   rsync -r "Игрушки для школьников"/ "Игрушки для дошколят"/ "Имя Игрушки"
   ```

#### 5. Переименовать директорию «Имя Игрушки» в «Игрушки»

   ```
   mv Имя\ Игрушки Игрушки
   ```

#### 6. Просмотреть содержимое каталога «Игрушки»

   ```
   ls -al Игрушки
   ```

#### 7. Установить и удалить snap-пакет. (Любой, какой хотите)

   ```
   sudo snap install  solitaire
   
   ```
   ```
   sudo snap remove  solitaire 
 
   ```

#### 8. Добавить произвольную задачу для выполнения каждые 3 минуты (Например, запись в текстовый файл чего-то или копирование из каталога А в каталог Б)
####   ***# Создаю файл script.sh и заливаю в него "код" скрипта, повышаю права файлу на исполнение, прописываю запуск в crontab***  
   ```
   echo -e '#! /bin/bash\necho $(date +%c) Новая запись >> test.txt' > script.sh 
   ```
   ```   
   chmod u+x /home/user/script.sh
   ```
   ```
   (crontab -l; echo "*/3 * * * * /home/user/script.sh")|awk '!x[$0]++'|crontab -
   ```
####   ***# Каждые 3 минуты будет производиться запись времени и даты в файл text.txt (при отсутствии файла, он будет создан через три минуты)***  
