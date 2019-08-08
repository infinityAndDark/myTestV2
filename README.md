# About the test
Enviroment:
+ Adnroid Studio 3.4.2 / Windows 10
+ Kotlin, androidx, retrofit

Sourcecode structure:
UI->(databinding)->ViewModel-->Data(Network)

Algorithms for break line of text:
- Input: keyword (text).
- Output: If the keyword is more than one word, then display in two lines. These two lines should have minimum difference in length.

- Solution:
 (*Use SPACE=' ', BREAK='\n')
  First, validate Input:
    --> Remove redundant SPACE, save new Input as ORIGIN ("Nguyễn     Nhật Ánh"--> "Nguyễn Nhật Ánh")
    --> Remove accent Chars, save as NO_ACCENT_INPUT ("Nguyễn Nhật Ánh" --> "Nguyen Nhat Anh")
  Convert NO_ACCENT_INPUT to list of word as WORDS (each word represent by it's length) and working with it:
  + Size of WORDS: 0 or 1 --> return ORIGIN
  + Size of WORDS: 2 --> replace SPACE by BREAK in ORIGIN and return
  + Size of WORDS: > 2 
    --> find the correct position to insert BREAK:
     Create two line of words, run in loop, each step move 1 word from LINE_TWO to LINE_ONE and compare length of two line:
     + BREAK_INDEX: run from 1, and +1 each step
     + DIFFERENCE_LENGTH: 0 or 1 --> return BREAK_INDEX
     + DIFFERENCE_LENGTH: LINE_ONE > LINE_TWO, 
       compare current with pre step --> choose less than --> return BREAK_INDEX
     + Create RESULT keyword with ORIGIN and BREAK_INDEX
     
(*Chia keyword thành các từ, tạo ra dòng chữ, mỗi dòng chứa 1 phần keyword, chạy vòng lặp di chuyển từng từ ở dòng dưới lên dòng trên và so sánh sự khác biệt về độ dài giữa 2 dòng, tìm được chênh lệch nhỏ nhất thì dừng lại)
    
