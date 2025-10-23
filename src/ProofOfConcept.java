import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

class ProofOfConcept {
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        MusicPlayer myPlaylist = new MusicPlayer();

        // Showcase adding songs:
        myPlaylist.addSong("Dogs", "Pink Floyd");
        myPlaylist.addSong("Pigs (Three Different Ones)", "Pink Floyd");
        myPlaylist.addSong("Sheep", "Pink Floyd");
        myPlaylist.addSong("crossing field", "LiSA");

        // Showcase skipping
        out.println("Current Song: " + myPlaylist.currentSongToString());
        myPlaylist.seekForward();
        out.println("Current Song: " + myPlaylist.currentSongToString());

        // Showcase loop from front to back
        myPlaylist.seekBackward();
        myPlaylist.seekBackward();
        out.println("Current Song: " + myPlaylist.currentSongToString());

        // Showcase removing songs and looping back to front:
        myPlaylist.removeSong("Dogs", "Pink Floyd");
        out.println("Current Song: " + myPlaylist.currentSongToString());
        myPlaylist.seekForward();
        out.println("Current Song: " + myPlaylist.currentSongToString());
    }

    public static class MusicPlayer {
        /*
         * Private members
         */

        private class Song {
            // Private members:
            // Some sample parameters for the Song subclass:
            private String title;
            private String artist;

            // Constructor:
            public Song(String title, String artist) {
                this.title = title;
                this.artist = artist;
            }

            @Override
            public final String toString() {
                String returnString = "";
                returnString += this.title;
                returnString += " by ";
                returnString += this.artist;

                return returnString;
            }

            public final boolean equals(Song song2) {
                boolean returnValue = false;
                if (this.title == song2.title && this.artist == song2.artist) {
                    returnValue = true;
                }

                return returnValue;
            }
        }

        // Representation:
        private Sequence<Song> songList;
        // Stores the index of the currently "playing" song
        private int currentSongIndex;

        private void createNewRep() {
            this.songList = new Sequence1L<Song>();
            this.currentSongIndex = 0;
        }

        /**
         * No-argument constructor.
         */
        MusicPlayer() {
            this.createNewRep();
        }

        /**
         *
         * @param title
         * @param artist
         */
        public final void addSong(String title, String artist) {
            Song newSong = new Song(title, artist);
            this.songList.add(this.songList.length(), newSong);
        }

        /**
         *
         */
        public final void removeSong(String title, String artist) {
            Song currSong = this.songList.entry(0);
            Song searchSong = new Song(title, artist);
            int index = 0;
            while (!currSong.equals(searchSong)) {
                currSong = this.songList.entry(index);
                index++;
            }
            this.songList.remove(index);
            if (index < this.currentSongIndex) {
                this.currentSongIndex--;
            }
        }

        public final void seekForward() {
            this.currentSongIndex++;
            if (this.currentSongIndex >= this.songList.length()) {
                this.currentSongIndex = 0;
            }
        }

        /**
         *
         */
        public final void seekBackward() {
            this.currentSongIndex--;
            if (this.currentSongIndex < 0) {
                this.currentSongIndex = this.songList.length() - 1;
            }
        }

        /**
         *
         * @return
         */
        public final String currentSongToString() {
            return this.songList.entry(this.currentSongIndex).toString();
        }
    }
}