import { Component, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlert, JhiAlertService, JhiEventManager, JhiLanguageService, JhiParseLinks, JhiTranslateComponent } from 'ng-jhipster';
import { FormControl } from '@angular/forms';
import { Observable, Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, startWith } from 'rxjs/operators';
import { TranslationLineMoodService } from 'app/entities/translation-line-mood/translation-line-mood.service.ts';
import { LoginModalService, AccountService, Account, JhiLanguageHelper } from 'app/core';
import { ITranslationLineMood, TranslationLineMood } from 'app/shared/model/translation-line-mood.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { MatPaginator, MatSort, MatTableDataSource, PageEvent } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/shared';
import { NavbarService } from '../layouts/navbar/navbar.service';
import { FooterService } from 'app/layouts/footer/footer.service';
import { HomeService } from './home.service';
import { fromEvent } from 'rxjs/internal/observable/fromEvent';
import { IVideoMood, VideoMood } from 'app/shared/model/video-mood.model';
import { VideoMoodService } from 'app/entities/video-mood';
import { faFacebookSquare } from '@fortawesome/free-brands-svg-icons/faFacebookSquare';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss', '../../newtheme/scss/stylish-portfolio.scss']
})
export class HomeComponent implements OnInit {
    fbIcon = faFacebookSquare;
    shareURL: string;
    selected: VideoMood;
    translationLines: ITranslationLineMood[];
    videos: IVideoMood[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    dataSource: any;

    alerts = [];

    length: number;
    pageSizeOptions: number[] = [20];

    // MatPaginator Output
    pageEvent: PageEvent;

    displayedColumns: string[] = ['text', 'episode', 'soundcloud'];

    account: Account;
    modalRef: NgbModalRef;
    // search: string;
    table: string;
    json: string;

    modelChanged: Subject<string> = new Subject<string>();
    private paramEpsiode: string;
    private paramQuery: string;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        public nav: NavbarService,
        public fot: FooterService,
        public homeService: HomeService,
        public videoMoodService: VideoMoodService,
        private route: ActivatedRoute
    ) {
        this.modelChanged
            .pipe(
                debounceTime(200),
                distinctUntilChanged()
            )
            .subscribe(model => {
                this.search(model);
            });
        this.homeService.getVideos().subscribe(
            (res: HttpResponse<IVideoMood[]>) => {
                this.videos = res.body;
                this.getParamsOnStart();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.translationLines = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    changed(text: string) {
        this.modelChanged.next(text);
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    // all from translation mood service .ts

    ngOnInit() {
        this.nav.hide();
        this.fot.hide();
        // this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTranslationLines();
    }

    getParamsOnStart() {
        this.route.queryParamMap.subscribe(queryParams => {
            this.paramEpsiode = this.route.snapshot.queryParamMap.get('episode');
            this.paramQuery = this.route.snapshot.queryParamMap.get('query');
            if (this.paramQuery === null) {
                this.paramQuery = '';
            }
            this.currentSearch = this.paramQuery;
            if (this.paramEpsiode !== null) {
                if (Number(this.paramEpsiode) > this.videos.length) {
                    const newAlert: JhiAlert = {
                        type: 'danger',
                        msg: 'home.notfound',
                        params: { episode: this.paramEpsiode },
                        timeout: 5000,
                        toast: false,
                        scoped: true
                    };
                    this.alerts.push(this.jhiAlertService.addAlert(newAlert, this.alerts));
                }
                this.selected = this.videos[Number(this.paramEpsiode) - 1];
            } else {
                this.selected = undefined;
            }
            this.search(this.paramQuery);
        });
    }

    timeStartAt(timeStart) {
        const splitted = timeStart.split(':', 3);
        const hour = splitted[0];
        const minute = splitted[1];
        let second = splitted[2];
        second = second.substring(0, 2);
        const start = hour + 'h' + +minute + 'm' + second + 's';
        return start;
    }

    soundcloudlink(episode, timeStart) {
        // tslint:disable-next-line:no-shadowed-variable
        const video = this.videos.find(video => video.episode === episode);
        const splitted = timeStart.split(':', 3);
        const hour = splitted[0];
        const minute = splitted[1];
        let second = splitted[2];
        second = second.substring(0, 2);
        return video.soundcloud + '#t=' + hour + ':' + +minute + ':' + second;
    }

    youtubelink(episode, timeStart) {
        // tslint:disable-next-line:no-shadowed-variable
        const video = this.videos.find(video => video.episode === episode);
        const splitted = timeStart.split(':', 3);
        const hour = parseInt(splitted[0], 10);
        const minute = parseInt(splitted[1], 10);
        let second = splitted[2];
        second = parseInt(second.substring(0, 2), 10);
        const time = second + minute * 60 + hour * 3600;
        const link = 'https://youtu.be/' + video.videoId + '?t=' + time;
        // const link = 'https://www.youtube.com/watch?v=' + video.videoId + 't=' + time + 's';
        return link;
    }

    loadAll() {
        if (this.selected === undefined) {
            this.selected = new TranslationLineMood();
            this.selected.episode = 0;
        }
        this.homeService
            .search({
                query: this.currentSearch,
                episode: this.selected.episode,
                page: this.page
            })
            .subscribe(
                (res: HttpResponse<ITranslationLineMood[]>) => {
                    this.paginateTranslationLines(res.body, res.headers);
                    this.dataSource = new MatTableDataSource(res.body);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.homeService
            .searchCount({
                query: this.currentSearch,
                episode: this.selected.episode
            })
            .subscribe(
                (res: HttpResponse<number>) => {
                    this.length = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.translationLines = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    loadMatPage(e) {
        this.page = e.pageIndex;
        this.loadAll();
    }

    clear() {
        this.translationLines = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.setShareURL();
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.translationLines = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        // this.currentSearch = query;
        this.setShareURL();
        this.loadAll();
    }

    // tslint:disable-next-line:use-life-cycle-interface
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTranslationLines() {
        this.eventSubscriber = this.eventManager.subscribe('translationLineListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateTranslationLines(data: ITranslationLineMood[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.translationLines.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    setClasses(alert) {
        return {
            toast: !!alert.toast,
            [alert.position]: true
        };
    }

    setShareURL() {
        let episode = 0;
        if (this.selected !== undefined) {
            episode = this.selected.episode;
        }
        let host = 'https://hacksuche.de/#/?';
        if (this.currentSearch === '') {
            host = host + 'episode=' + episode;
        } else {
            host = host + 'query=' + this.currentSearch;
            host = host + '&episode=' + episode;
        }
        this.shareURL = host;
    }
}
