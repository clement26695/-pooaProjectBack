package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieDBImporter {

    private SerieImporter serieImporter;
    private SeasonImporter seasonImporter;
    private EpisodeImporter episodeImporter;
    private GenreImporter genreImporter;
    private ActorImporter actorImporter;
    private DirectorImporter directorImporter;

    private SerieRepository serieRepository;
    private SeasonRepository seasonRepository;
    private EpisodeRepository episodeRepository;

    public MovieDBImporter(SerieImporter serieImporter, SeasonImporter seasonImporter, EpisodeImporter episodeImporter,
            GenreImporter genreImporter, ActorImporter actorImporter, DirectorImporter directorImporter,
            SerieRepository serieRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
		super();
		this.serieImporter = serieImporter;
		this.seasonImporter = seasonImporter;
		this.episodeImporter = episodeImporter;
		this.genreImporter = genreImporter;
		this.actorImporter = actorImporter;
        this.directorImporter = directorImporter;
        this.serieRepository = serieRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
	}


    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importDataFromTMDBApi() {
        // System.out.println("1");
        // this.serieImporter.importSerie(1);
        // System.out.println("2");
        Iterable<Serie> series = this.serieRepository.findAll();
        for (Serie serie : series) {
            // System.out.println("Import seasons from : " + serie.getName());
            // this.seasonImporter.importSeason(serie);
            System.out.println("Import genres from : " + serie.getName());
            this.genreImporter.importGenre(serie);
        }

        // Iterable<Season> seasons = this.seasonRepository.findAll();
        // for (Season season : seasons) {
        //     System.out.println("Import episodes from : " + season.getName());
        //     this.episodeImporter.importEpisode(season);
        // }

        // Iterable<Episode> episodes = this.episodeRepository.findAll();
        // for (Episode episode : episodes) {
        //     this.actorImporter.importActor(episode);
        //     this.directorImporter.importDirector(episode);
        // }
    }
}
