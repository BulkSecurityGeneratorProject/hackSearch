import { Component, OnInit } from '@angular/core';
import { HiThereService } from 'app/hi-there/hi-there.service';

@Component({
    selector: 'jhi-hi-there',
    templateUrl: './hi-there.component.html',
    styleUrls: ['hi-there.component.scss']
})
export class HiThereComponent implements OnInit {
    message: string;

    constructor(protected hiThereService: HiThereService) {
        this.message = 'HiThereComponent message';
    }

    ngOnInit() {}

    checkVideos() {
        this.hiThereService.checkVideos().subscribe(res => {
            res.body;
        });
    }

    checkNewCaptions() {
        this.hiThereService.checkNewCaptions().subscribe(res => {
            res.body;
        });
    }

    checkNewCaptionLines() {
        this.hiThereService.checkNewCaptionLines().subscribe(res => {
            res.body;
        });
    }

    getYouTubeService() {
        this.hiThereService.getYouTubeService().subscribe(res => {
            res.body;
        });
    }

    setEpisodeinTranslation() {
        this.hiThereService.setEpisodeinTranslation().subscribe(res => {
            res.body;
        });
    }

    checkAll() {
        this.hiThereService.checkAll().subscribe(res => {
            res.body;
        });
    }
}
