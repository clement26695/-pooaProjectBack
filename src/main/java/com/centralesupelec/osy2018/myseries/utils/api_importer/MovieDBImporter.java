package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;
import com.centralesupelec.osy2018.myseries.utils.NotificationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieDBImporter {

    Logger logger = LoggerFactory.getLogger(NotificationUtils.class);

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
        logger.debug("Start Importation");
        try {
            this.serieImporter.importSerie(1);
            Thread.sleep(250);

            Iterable<Serie> series = this.serieRepository.findAll();
            for (Serie serie : series) {
                logger.debug("Import seasons from : " + serie.getName());
                this.seasonImporter.importSeason(serie);
                Thread.sleep(250);
                logger.debug("Import genres from : " + serie.getName());
                this.genreImporter.importGenre(serie);
                Thread.sleep(250);
            }

            Iterable<Season> seasons = this.seasonRepository.findAll();
            for (Season season : seasons) {
                logger.debug("Import episodes from : " + season.getName());
                this.episodeImporter.importEpisode(season);
                Thread.sleep(250);
            }

            // Iterable<Episode> episodes = this.episodeRepository.findAll();
            // for (Episode episode : episodes) {
            //     this.actorImporter.importActor(episode);
            //     Thread.sleep(250);
            //     this.directorImporter.importDirector(episode);
            //     Thread.sleep(250);
            // }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.debug("End Importation");
        }

    }
}
