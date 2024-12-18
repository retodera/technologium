package technologium.content;

import arc.graphics.Color;
import mindustry.game.Team;

//credits to FOS (Fictional Octo System) mod
public class TTeams {
    public static Team kaut;

    public static void load(){
        kaut = newTeam(28, "kaut", Color.valueOf("bf8f6f"));
    }

    //modify any of 256 teams' properties
    private static Team newTeam(int id, String name, Color color) {
        Team team = Team.get(id);
        team.name = name;
        team.color.set(color);

        team.palette[0] = color;
        team.palette[1] = color.cpy().mul(0.75f);
        team.palette[2] = color.cpy().mul(0.5f);

        for(int i = 0; i < 3; i++){
            team.palettei[i] = team.palette[i].rgba();
        }
        return team;
    }
}