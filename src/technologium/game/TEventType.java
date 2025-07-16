package technologium.game;

import arc.audio.Music;

public class TEventType {
    public static enum TTrigger {
        welcoming1start,
        welcoming1end,
        welcoming2start,
        welcoming2end,
        treeGrown
    }

    public static class MusicChangeEvent {
        public Music from, to;
        
        public MusicChangeEvent(Music from, Music to) {
            this.from = from;
            this.to = to;
        }
    }
}
