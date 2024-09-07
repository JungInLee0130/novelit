package com.galaxy.novelit;

import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.repository.PlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class NovelitApplicationTests {

	@Autowired
	PlotRepository plotRepository;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void init(){
		Plot plot = Plot.builder()
				.workspaceUuid("asdf-asdf")
				.plotUuid("asdf-asdf")
				.plotTitle("해리포터 1장")
				.story("ㅇㅇ")
				.beginning("ㅇㅇ")
				.rising("ㅇㅇ")
				.crisis("ㅇㅇ")
				.climax("ㅇㅇ")
				.ending("ㅇㅇ")
				.build();

		plotRepository.save(plot);
	}
}
