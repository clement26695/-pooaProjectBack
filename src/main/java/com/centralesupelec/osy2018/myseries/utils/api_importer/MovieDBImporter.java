package com.centralesupelec.osy2018.myseries.utils.api_importer;

import com.centralesupelec.osy2018.myseries.models.Episode;
import com.centralesupelec.osy2018.myseries.models.Season;
import com.centralesupelec.osy2018.myseries.models.Serie;
import com.centralesupelec.osy2018.myseries.repository.EpisodeRepository;
import com.centralesupelec.osy2018.myseries.repository.SeasonRepository;
import com.centralesupelec.osy2018.myseries.repository.SerieRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieDBImporter {

    Logger logger = LoggerFactory.getLogger(MovieDBImporter.class);

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
        logger.info("Start Importation");
        try {
            this.serieImporter.importSerie(10, false);
            Thread.sleep(250);

            Iterable<Serie> series = this.serieRepository.findAll();
            for (Serie serie : series) {
                logger.info("Import seasons from : " + serie.getName());
                this.seasonImporter.importSeason(serie);
                Thread.sleep(250);
                logger.info("Import genres from : " + serie.getName());
                this.genreImporter.importGenre(serie);
                Thread.sleep(250);
                logger.info("Import episode run time from : " + serie.getName());
                this.serieImporter.importEpisodeRunTime(serie);
                Thread.sleep(250);
                logger.info("Import actor from : " + serie.getName());
                this.actorImporter.importActor(serie);
                Thread.sleep(250);
            }

            Iterable<Season> seasons = this.seasonRepository.findAll();
            int count = 0;
            for (Season season : seasons) {
                logger.info(count + " - Import episodes from : " + season.getSerie().getName() + " - " + season.getSeasonNumber());
                this.episodeImporter.importEpisode(season, false);
                count += 1;
                Thread.sleep(250);
            }

            Iterable<Episode> episodes = this.episodeRepository.findAll();
            for (Episode episode : episodes) {
                this.actorImporter.importActor(episode);
                Thread.sleep(250);
                this.directorImporter.importDirector(episode);
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("End Importation");
        }

    }
}
