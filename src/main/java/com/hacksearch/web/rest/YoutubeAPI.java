package com.hacksearch.web.rest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.hacksearch.ParseTranscript;
import com.hacksearch.service.*;
import com.hacksearch.service.dto.CaptionCriteria;
import com.hacksearch.service.dto.CaptionDTO;
import com.hacksearch.service.dto.VideoDTO;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class YoutubeAPI {
//    private final VideoService videoService;
//
////    private final VideoQueryService videoQueryService;
//
//    public CaptionsGetId(VideoService videoService) {
//        this.videoService = videoService;
//    }

    @Autowired
    VideoService videoService;

    @Autowired
    VideoServiceExt videoServiceExt;

    @Autowired
    CaptionService captionService;

    @Autowired
    CaptionQueryService captionQueryService;

    @Autowired
    TranslationLineService translationLineService;

    @Autowired
    TranslationLineQueryService translationLineQueryService;

    @Autowired
    ParseTranscript parseTranscript;

    private final Logger log = LoggerFactory.getLogger(YoutubeAPI.class);

    private static final String PLAYLISTID = "PLEKG_YBLX5yUsKbhCiOGIBRwXyBsO17bz";
    private static final String CLIENT_SECRETS = "client_secret.json";
    private static final Collection<String> SCOPES =
        Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private YouTube youTubeService;

    @PostConstruct
    public void createService() {
        try {
            youTubeService = getService();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        Credential credential = authorize(httpTransport);
        Credential credential = serviceAccountAuthorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public Credential serviceAccountAuthorize(final NetHttpTransport httpTransport) throws IOException {
        List<String> scopes = new ArrayList<>();
        scopes.add("https://www.googleapis.com/auth/youtube");
        scopes.add("https://www.googleapis.com/auth/youtube.force-ssl");
        final HttpTransport TRANSPORT = new NetHttpTransport();
        final JsonFactory JSON_FACTORY = new JacksonFactory();


        InputStream jsonFileStream =
            YoutubeAPI.class.getResourceAsStream("/hackSearch-14bcf7610e0e.json");

        GoogleCredential credential = GoogleCredential
            .fromStream(jsonFileStream, httpTransport, JSON_FACTORY).createScoped(scopes);

        return credential;
    }

    public Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
            new InputStreamReader(YoutubeAPI.class.getResourceAsStream("/client_secrets.json")));
//        GoogleClientSecrets clientSecrets =
//            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .build();
//        Credential credential =
//            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setHost(host).setPort(port).setCallbackPath("/Callback").build()).authorize("user");
        Credential credential =
            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
//    private static Credential authorize() throws Exception {
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        // load client secrets
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//            new InputStreamReader(ApiExample.class.getResourceAsStream("/client_secrets.json")));
//        // set up authorization code flow
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//            httpTransport, JSON_FACTORY, clientSecrets,
//            Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
//            .build();
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//            httpTransport, JSON_FACTORY, clientSecrets,
//            Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(dataStoreFactory)
//            .build();
//        // authorize
//        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//    }
    @Async
    public Future<Void> getNewVideos() {
        log.info("searching for new Videos");
        List<PlaylistItem> playlistItems1 = new ArrayList<>();
        List<PlaylistItem> playlistItems2 = new ArrayList<>();
        List<PlaylistItem> playlistItems = new ArrayList<>();
        try {
            //get first fifty playlist results
            YouTube.PlaylistItems.List playlistRequest1 = youTubeService.playlistItems()
                .list("snippet");
            PlaylistItemListResponse playlistResponse1 = playlistRequest1.setMaxResults(50L)
                .setPlaylistId(PLAYLISTID)
                .execute();
            playlistItems1 = playlistResponse1.getItems();

            // get the other playlist
            YouTube.PlaylistItems.List playlistRequest2 = youTubeService.playlistItems()
                .list("snippet");
            PlaylistItemListResponse playlistResponse2 = playlistRequest2.setPageToken(playlistResponse1.getNextPageToken())
                .setPlaylistId(PLAYLISTID)
                .setMaxResults(50L)
                .execute();
            playlistItems2 = playlistResponse2.getItems();
            playlistItems.addAll(playlistItems1);
            playlistItems.addAll(playlistItems2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<VideoDTO> videoDTOList = videoService.findAll();

        for (PlaylistItem item : playlistItems) {
            Boolean found = false;
            for (VideoDTO video : videoDTOList) {
                if (item.getSnippet().getResourceId().getVideoId().equals(video.getVideoId())) {
                    found = true;
                }
            }
            if (!found) {
                VideoDTO newVideo = new VideoDTO();
                newVideo.setTitle(item.getSnippet().getTitle());
                newVideo.setVideoId(item.getSnippet().getResourceId().getVideoId());
                log.info("found new video: " + item.getSnippet().getTitle());
                videoService.save(newVideo);
            }
        }
        // get all videos from database
        // check if some does not exist
        // add them to the database

        log.info("finished searching for new Videos");
        return null;
    }

    @Async
    public Future<Void> getNewTranslations() throws IOException {
        log.info("search for new Captions");
        //get all videos in DB
        List<VideoDTO> videoDTOList = videoService.findAll();

        for (VideoDTO video : videoDTOList) {
//
            CaptionCriteria captionCriteria = new CaptionCriteria();
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(video.getId());
            captionCriteria.setVideoIdId(longFilter);
            // get video in captions Table
            List<CaptionDTO> captionDTOList = captionQueryService.findByCriteria(captionCriteria);
            CaptionDTO captionDTO = captionDTOList.stream().filter(caption -> "ASR".equals(caption.getTrackKind())).findFirst().orElse(null);
            //if ASR Caption not found, get it
            if (captionDTO == null) {
                log.info("no captions in DB found for " + video.getTitle());
                // get complete list of translations
                YouTube.Captions.List captionsListRequest = youTubeService.captions()
                    .list("snippet", video.getVideoId());
                CaptionListResponse captionsListesponse = captionsListRequest.execute();
                List<Caption> captions = captionsListesponse.getItems();
                // check if in list is ASR
                Caption caption = captions.stream().filter(captionResponse -> "ASR".equals(captionResponse.getSnippet().getTrackKind())).findFirst().orElse(null);
                //if ASR found save to DB
                if (caption != null) {
                    log.info("ASR captions found");
                    CaptionDTO captionDTO1 = new CaptionDTO();
                    captionDTO1.setVideoIdId(video.getId());
                    captionDTO1.setCaptionId(caption.getId());
                    captionDTO1.setLanguage(caption.getSnippet().getLanguage());
                    captionDTO1.setTrackKind(caption.getSnippet().getTrackKind());
                    captionService.save(captionDTO1);
                } else {
                    log.info("no ASR captions on youtube found");
                }
            }
        }
        log.info("finished search for new Captions");
        return null;
    }

    @Async
    public Future<Void> getNewCaptionsDownload() throws IOException {
        log.info("search for new captions to download");
        List<VideoDTO> videoDTOList = videoService.findAll();

        for (VideoDTO video : videoDTOList) {
            List<CaptionDTO> captionDTOList = getCaptionByVideoId(video);
            CaptionDTO captionDTO =
                captionDTOList.stream()
                    .filter(caption -> "ASR".equals(caption.getTrackKind())).findFirst().orElse(null);
            // when caption found but no translations, download
            if (captionDTO != null && getCountTranslationsByCaption(captionDTO).intValue() == 0) {
                log.info("np captions downloaded for:" + video.getTitle() + ", download now captions");
                YouTube.Captions.Download captionsDownloadRequest = youTubeService.captions().download(captionDTO.getCaptionId());
                captionsDownloadRequest.getMediaHttpDownloader();
                parseTranscript.parseInputStream(captionsDownloadRequest.executeMediaAsInputStream(), captionDTO.getId());


            }
        }
        //get videos
        //get captions
        // check if captions lines exists per caption
        // download caption lines
        log.info("finished search for new captions to download");
        return null;
    }

    @Async
    public Future<Void> setEpisodeInTranslations(){
        log.info("setting episode in translations started");
        videoServiceExt.setEpisodeInTranslation();
        log.info("setting episode in translations finished");
        return null;
    }

    @Async
    public void checkAll(){
        log.info("checking all in once");
        Future<Void> futureVideos =  new CompletableFuture();
        Future<Void> futureTransl =  new CompletableFuture();
        Future<Void> futureTransDown =  new CompletableFuture();
        Future<Void> futuresetEpisodes =  new CompletableFuture();
        try {
            futureVideos = getNewVideos();
            futureTransl = getNewTranslations();
            futureTransDown= getNewCaptionsDownload();
            futuresetEpisodes = setEpisodeInTranslations();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            futureVideos.get();
            futureTransl.get();
            futureTransDown.get();
            futuresetEpisodes.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        log.info("setting episode in translations finished");
    }


    public List<CaptionDTO> getCaptionByVideoId(VideoDTO video) {
        CaptionCriteria captionCriteria = new CaptionCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(video.getId());
        captionCriteria.setVideoIdId(longFilter);
        return captionQueryService.findByCriteria(captionCriteria);
    }

    public Integer getCountTranslationsByCaption(CaptionDTO captionDTO) {
//        TranslationLineCriteria translationLineCriteria = new TranslationLineCriteria();
//        LongFilter longFilter = new LongFilter();
//        longFilter.setEquals(caption.getId());
//        translationLineCriteria.setCaptionIdId(longFilter);
//        Long count = translationLineQueryService.countByCriteria(translationLineCriteria);
        com.hacksearch.domain.Caption caption = translationDTOtoEntity(captionDTO);
        return translationLineService.countTranslationLineByCaptionIdId(caption);
    }

    public com.hacksearch.domain.Caption translationDTOtoEntity(CaptionDTO captionDTO) {
        com.hacksearch.domain.Caption caption = new com.hacksearch.domain.Caption();
        caption.setId(captionDTO.getId());
        caption.setCaptionId(captionDTO.getCaptionId());
        caption.setLanguage(captionDTO.getLanguage());
        caption.setTrackKind(captionDTO.getTrackKind());
        return caption;
    }
}

