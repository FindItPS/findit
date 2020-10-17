import scrapy
from scrapy_selenium import SeleniumRequest
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC


class TechSpider(scrapy.Spider):
    name = "tech"

    def start_requests(self):
        urls = [
            'https://www.canadacomputers.com/search/results_details.php?keywords=laptop',
        ]
        script = 'for(let x=0;x<4;x++) { window.setTimeout(() => window.scrollTo(0,document.body.scrollHeight), 6000); }' # Arbitrarily chose to scroll 4 times
        for url in urls:
            yield SeleniumRequest(url=url, 
            callback=self.parse,
            script=script, # If we want to get more results, we will have to deal with the scrollign timeout
            script=script, # If we want to get more results, we will have to deal with the scrolling timeout
            )

    def parse(self, response):
        page = response.url.split("/")[-2]
        filename = f'tech{page}.html'
        with open(filename, 'wb') as f:
            f.write(response.body)
        self.log(f'Saved file {filename}')