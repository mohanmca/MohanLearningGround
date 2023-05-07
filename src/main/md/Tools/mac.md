 ## How to find java_home
 1. /usr/libexec/java_home -V
 1. /Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home/
 1. /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home

## How to uninstall all the JDK
sudo rm -fr /Users/alpha/Library/Java/JavaVirtualMachines/
sudo rm -rf /opt/homebrew/Cellar/openjdk
brew cask install adoptopenjdk8
brew reinstall --cask adoptopenjdk8
 
/opt/homebrew/opt/openjdk/
## Openjdk@17

1. /opt/homebrew/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
1  echo 'export PATH="/opt/homebrew/opt/openjdk/bin:$PATH"' >> ~/.zshrc

## Brew Commands

1. brew services start redis
1. brew services stop redis
1. brew services help

## Most often used
1. Show hidden file in finder - ```Command + Shift + .```
2. Portion of Screenshot (4) 
   1. to file - ```Command-Shift-4```
   1. to clipboard - ```Command-Control-Shift-4```
3. Entire Screenshot (3)
   1. to file - ```Command-Shift-3```
   1. to clipbord - ```Command-Control-Shift-3```

## Update software tools
```shell
softwareupdate --all --install --force
sudo rm -rf /Library/Developer/CommandLineTools
sudo xcode-select --install
```
   
## Spotlight Search

* Command + Space

## Copy/Paste/Cut

* Command + c / Command + V / Command + X

## Kill application

* Command + Option + Escape
* Command + Option + Escape + Shift (force close current application in front - when taskmgr not showing)

## Chrome + Safari

* Command + F – Start searching the current page. This also works in other applications.
* Command + Left Arrow – Go back a page.
* Command + Right Arrow – Go forward a page.
* Command + T – Open a new tab.
* Command + W – Close the current tab.
* Command + L – Focus the browser’s location bar so you can start typing a search or web address immediately.
* Ctrl + Tab – Switch between open tabs.
* Ctrl + Shift + Tab – Switch between open tabs in reverse.

## Text Editing

* Command + A – Select All
* Command + Z – Undo
* Command + Shift + Z – Redo
* Command + Left Arrow –  Go to the beginning of the current line.
* Command + Right Arrow – Go to the end of the current line.
* Option + Left Arrow – Move the cursor left one word.
* Option + Right Arrow – Move the cursor right one word.
* Option + Delete – Delete the word to the left of the cursor. Bear in mind that the Delete button on a Mac functions like Backspace on Windows.

## Managing Open Applications

* Command + Tab – Move through a list of open applications. This is like Alt + Tab on Windows.
* Command + Shift + Tab – Move through the list in reverse.
* Command + Q – Quit the current application. This is like Alt + F4 on Windows.
* F3 – Open Mission Control to view all open application windows and desktops.
* Ctrl + Left Arrow – Move one desktop to the left.
* Ctrl + Right Arrow – Move one desktop to the right.

## Mac vs Windows - Alt key

* Option in MAC key locates the same place where WIN key is present in windows keyboard
* Alt Tab to switch windows vs Option Tab
* Alt key position on windows 3rd right (just before space) vs stuffed in middle on MAC


## Mac vs Windows - Ctl key
* Windows-OS Save - Ctrl+S      vs   on Windows-Key-S while using MAC
* Windows-OS Copy/paste - Ctrl-C / Ctrl-V   vs  on Windows-Key-C and Windows-Key-V while using MAC

## My Keyboard
* MAC identified my keyboard as USB-HID Gaming Keyboard


## Options we have when switching MAC
* Map windows Ctrl to MAC Command (and vice versa)


