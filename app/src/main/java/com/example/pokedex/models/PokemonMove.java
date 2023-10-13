package com.example.pokedex.models;


import java.util.ArrayList;

public class PokemonMove {
    private Move move;
    private ArrayList<VersionGroupDetail> versionGroupDetails;

    public Move getMove() {
        return move;
    }

    public ArrayList<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public class Move {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public class VersionGroupDetail {
        private int levelLearnedAt;
        private MoveLearnMethod moveLearnMethod;
        private VersionGroup versionGroup;

        public int getLevelLearnedAt() {
            return levelLearnedAt;
        }

        public MoveLearnMethod getMoveLearnMethod() {
            return moveLearnMethod;
        }

        public VersionGroup getVersionGroup() {
            return versionGroup;
        }
    }

    public class MoveLearnMethod {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }

    public class VersionGroup {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}