package technologium.entities.abilities;

import arc.audio.Music;
import mindustry.gen.Unit;

//partially stolen from NightScape
public class MusicAbility extends TAbility {
    private boolean loop = true;
    public Music music;
    public float end = 0;
    public Music endMusic;
    public float endMusicPos = 0;

    @Override
    public void update(Unit unit) {
        if(music == null) return;
        if(loop && !music.isPlaying()) music.play();
        music.setLooping(loop);
    }

    public void death(Unit unit) {
        loop = false;
        if(music != null) music.setLooping(loop);
        if(music != null && endMusic == null) music.setPosition(end);
        else {
            if(music != null) music.stop();
            endMusic.play();
            if(endMusicPos != 0) endMusic.setPosition(endMusicPos);
        }
    }
}
