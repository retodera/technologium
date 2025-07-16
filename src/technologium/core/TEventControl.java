package technologium.core;

import arc.audio.Music;
import technologium.TVars;
import arc.Events;

import static technologium.game.TEventType.*;

public class TEventControl{
    private static Music pastMusic = new Music();

    public void update() {
        Music newMusic = TVars.sound.current;
        if(newMusic != pastMusic) {
            Events.fire(new MusicChangeEvent(pastMusic, newMusic));
            pastMusic = newMusic;
        }
    }
}