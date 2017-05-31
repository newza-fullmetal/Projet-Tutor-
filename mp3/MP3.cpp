/*
 * Serial1Player_KT403A.cpp
 * A library for Grove-Serial Serial1 Player V2.0
 *
 * Copyright (c) 2015 seeed technology inc.
 * Website    : www.seeed.cc
 * Author     : Wuruibin
 * Created Time: Dec 2015
 * Modified Time:
 * 
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 
#include <Arduino.h>
#include "MP3.h"


// Note: You must define a SoftwareSerial class object that the name must be Serial1, 
//       but you can change the pin number according to the actual situation.
//SoftwareSerial Serial1(2, 3);         // define in the demo file 


/**************************************************************** 
 * Function Name: SelectPlayerDevice
 * Description: Select the player device, U DISK or SD card.
 * Parameters: 0x01:U DISK;  0x02:SD card
 * Return: none
****************************************************************/ 
void SelectPlayerDevice(uint8_t device)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x09);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(device);
    Serial1.write(0xEF);
    delay(200);
}

/**************************************************************** 
 * Function Name: SpecifyMusicPlay
 * Description: Specify the music index to play, the index is decided by the input sequence of the music.
 * Parameters: index: the music index: 0-65535.
 * Return: none
****************************************************************/ 
void SpecifyMusicPlay(uint16_t index)
{
    uint8_t hbyte, lbyte;
    hbyte = index / 256;
    lbyte = index % 256;
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x03);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(hbyte));
    Serial1.write(uint8_t(lbyte));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: SpecifyfolderPlay
 * Description: Specify the music index in the folder to play, the index is decided by the input sequence of the music.
 * Parameters: folder: folder name, must be number;  index: the music index.
 * Return: none
****************************************************************/ 
void SpecifyfolderPlay(uint8_t folder, uint8_t index)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x0F);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(folder));
    Serial1.write(uint8_t(index));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: PlayPause
 * Description: Pause the Serial1 player.
 * Parameters: none
 * Return: none
****************************************************************/ 
void PlayPause(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x0E);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
//  Serial1.write(0xFE);
//  Serial1.write(0xED);
    Serial1.write(0xEF);
    delay(20);
//  return true;
}

/**************************************************************** 
 * Function Name: PlayResume
 * Description: Resume the Serial1 player.
 * Parameters: none
 * Return: none
****************************************************************/ 
void PlayResume(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x0D);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
//  Serial1.write(0xFE);
//  Serial1.write(0xEE);
    Serial1.write(0xEF);
    delay(20);
//  return true;
}

/**************************************************************** 
 * Function Name: PlayNext
 * Description: Play the next song.
 * Parameters: none
 * Return: none
****************************************************************/ 
void PlayNext(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x01);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: PlayPrevious
 * Description: Play the previous song.
 * Parameters: none
 * Return: none
****************************************************************/ 
void PlayPrevious(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x02);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: PlayLoop
 * Description: Play loop for all the songs.
 * Parameters: none
 * Return: none
****************************************************************/ 
void PlayLoop(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x11);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(0x01);
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: SetVolume
 * Description: Set the volume, the range is 0x00 to 0x1E.
 * Parameters: volume: the range is 0x00 to 0x1E.
 * Return: none
****************************************************************/ 
void SetVolume(uint8_t volume)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x06);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(volume);
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: IncreaseVolume
 * Description: Increase the volume.
 * Parameters: none
 * Return: none
****************************************************************/ 
void IncreaseVolume(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x04);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: DecreaseVolume
 * Description: Decrease the volume.
 * Parameters: none
 * Return: none
****************************************************************/ 
void DecreaseVolume(void)
{
    Serial1.write(0x7E);
    Serial1.write(0xFF);
    Serial1.write(0x06);
    Serial1.write(0x05);
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(uint8_t(0x00));
    Serial1.write(0xEF);
    delay(10);
//  return true;
}

/**************************************************************** 
 * Function Name: printReturnedData
 * Description: Print the returned data that sent from the Grove_Serial_Serial1_Player.
 * Parameters: none
 * Return: none
****************************************************************/ 
void printReturnedData(void)
{
    unsigned char c;
    //check if there's any data sent from the Grove_Serial_Serial1_Player
    while(Serial1.available())
    {
        c = Serial1.read();
        Serial.print("0x");
        Serial.print(c, HEX);
        Serial.print(" ");
    }
    Serial.println(" "); 
}

/**************************************************************** 
 * Function Name: QueryPlayStatus
 * Description: Query play status.
 * Parameters: none
 * Return: 0: played out; 1: other.
 * Usage: while(QueryPlayStatus() != 0);  // Waiting to play out.
****************************************************************/ 
uint8_t QueryPlayStatus(void)
{
    unsigned char c[10] = {0};
    uint8_t i = 0;
    //check if there's any data sent from the Grove_Serial_Serial1_Player
    while(Serial1.available())
    {
        c[i] = Serial1.read();
        i++;
		delay(1);
		if (i == 10) break;
//        Serial.print(" 0x");
//        Serial.print(c[i], HEX);
    }
//    Serial.println(" "); 
    
    if(c[3] == 0x3C || c[3] == 0x3D || c[3] == 0x3E)
    {
        return 0;
    }
    else
    {
        return 1;
    }
}