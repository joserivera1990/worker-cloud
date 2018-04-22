package bash

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import bash.UtilVideo._
import com.typesafe.config.ConfigFactory
import models.Voice

object Video {

  def convertAudioToMp3(voice: Voice, pathInput: String, pathOutput: String): Unit =
    {
      val PATH_FFMPEG = ConfigFactory.load().getString("audio.ffmpeg")
      val PATH_FFPROBE = ConfigFactory.load().getString("audio.ffprobe")
      val processedPath = voice.filevoiceprocessed.get
      val path = voice.filevoiceoriginal.get

      val builder = new FFmpegBuilder().setInput(pathInput)
        .overrideOutputFiles(true)
        .addOutput(pathOutput)
        .setFormat(getFormatFile(voice.nameFile.get))
        .setAudioChannels(1)
        .setAudioSampleRate(48000)
        .setAudioBitRate(32768)
        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
        .done();

      val executor = new FFmpegExecutor(new FFmpeg(PATH_FFMPEG), new FFprobe(PATH_FFPROBE))

      executor.createJob(builder).run()
    }
}