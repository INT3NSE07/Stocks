import os
import pandas as pd
import yfinance as yf

S_AND_P_URL = 'https://en.wikipedia.org/wiki/List_of_S%26P_500_companies'
DOWNLOAD_PATH = os.path.join(os.getcwd(), 'stock_data.csv')

def get_ticker_data(tickers: list):
    #tickers = tickers[0:2]
    data = yf.download(
        tickers = tickers,
        period = '6mo',
        group_by = 'ticker',
        threads = True,
        rounding = True
    )

    data = data.drop('Adj Close', axis=1, level=1)

    for ticker in tickers:
        try:
            df = data.loc[:, ticker.upper()].dropna()
            df.insert(0, 'Symbol', ticker)

            df.to_csv(DOWNLOAD_PATH, mode='a', header=not os.path.exists(DOWNLOAD_PATH))
        except Exception as e:
            print(f'Ticker {ticker} failed to download. {e}')
    
    df = pd.read_csv(DOWNLOAD_PATH)
    
    df['Date'] = pd.to_datetime(df['Date']).dt.strftime('%Y-%m-%d')
    df = df.groupby('Symbol').apply(pd.DataFrame.sort_values, 'Date', ascending=False)

    df.to_csv(DOWNLOAD_PATH, index=False)

    return


def get_s_and_p_tickers() -> list:
    '''
    Get a list of all tickers currently in the S&P500 index.
    '''
    return pd.read_html(S_AND_P_URL)[0]['Symbol'].tolist()
    

if __name__ == '__main__':
    
    if os.path.isfile(DOWNLOAD_PATH):
        os.remove(DOWNLOAD_PATH)
    
    get_ticker_data(get_s_and_p_tickers())