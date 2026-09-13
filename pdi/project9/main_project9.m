% main_project9.m
% PDI - Projeto 7: Segmentacao - Fronteiras dos 2 maiores blobs
% Autor: Matheus Serrao Uchoa
%
% Pipeline:
%   1. Converter para escala de cinza
%   2. Limiarizacao de Otsu
%   3. Selecionar classe minoritaria (blobs = objetos, nao fundo)
%   4. Abertura + fechamento morfologico (remover ruido)
%   5. Rotulacao de componentes conectados
%   6. Selecionar 2 maiores componentes
%   7. Extrair fronteira: contorno = blob XOR erosao(blob)
%   8. Sobrepor contornos na imagem original

clear; clc; close all;

images = {'images project #7/Imagem1.jpg', ...
          'images project #7/Imagem2.jpg', ...
          'images project #7/Imagem3.jpg'};

colors_overlay = {[255  50  50], [50 200 255]};   % vermelho e ciano

for idx = 1:length(images)
  fprintf('\n=== %s ===\n', images{idx});

  % 1. Carrega imagem
  rgb  = imread(images{idx});
  gray = rgb2gray(rgb);

  % 2. Limiar de Otsu
  T = graythresh(gray);   % normalizado [0,1]
  fprintf('  Limiar Otsu: T = %.4f  (%.0f/255)\n', T, T*255);

  % 3. Binarizacao - usa classe minoritaria como objeto
  mask_light = imbinarize(gray, T);  % pixels acima do limiar
  mask_dark  = ~mask_light;

  if sum(mask_light(:)) <= sum(mask_dark(:))
    mask = mask_light;
    polarity = 'light';
  else
    mask = mask_dark;
    polarity = 'dark';
  end
  fprintf('  Polarity: %s  |  foreground: %.1f%%\n', polarity, 100*sum(mask(:))/numel(mask));

  % 4. Limpeza morfologica
  se = strel('disk', 3);
  mask_clean = imopen(mask, se);
  mask_clean = imclose(mask_clean, se);

  % 5. Rotulacao de componentes conectados
  cc = bwconncomp(mask_clean, 8);
  fprintf('  Componentes encontrados: %d\n', cc.NumObjects);

  % 6. Selecionar 2 maiores
  stats  = regionprops(cc, 'Area');
  areas  = [stats.Area];
  [sorted_areas, sort_idx] = sort(areas, 'descend');

  fprintf('  Areas dos 2 maiores: %d e %d px\n', sorted_areas(1), sorted_areas(2));

  % Mascaras dos 2 maiores blobs
  labeled = labelmatrix(cc);
  blob1 = (labeled == sort_idx(1));
  blob2 = (labeled == sort_idx(2));

  % 7. Extrair fronteiras (XOR com erosao)
  se_erode = strel('disk', 2);
  bnd1 = blob1 & ~imerode(blob1, se_erode);
  bnd2 = blob2 & ~imerode(blob2, se_erode);

  % 8. Sobrepor na imagem original
  overlay = rgb;
  for c = 1:3
    ch = overlay(:,:,c);
    ch(bnd1) = colors_overlay{1}(c);
    ch(bnd2) = colors_overlay{2}(c);
    overlay(:,:,c) = ch;
  end

  % --- Figura de resultados ---
  figure('Name', sprintf('Projeto 7 - %s', images{idx}), ...
         'Position', [50 50 1400 900]);

  subplot(2,3,1); imshow(rgb);        title('Original');
  subplot(2,3,2); imshow(gray,[]);    title(sprintf('Cinza  T=%.2f', T));
  subplot(2,3,3); imshow(mask);       title('Otsu binarizado');
  subplot(2,3,4); imshow(mask_clean); title('Apos limpeza morfologica');

  % Mapa de rotulos colorido
  label_rgb = label2rgb(labeled, 'jet', 'k', 'shuffle');
  subplot(2,3,5); imshow(label_rgb);
  title(sprintf('%d componentes  top2=%d,%d px', cc.NumObjects, sorted_areas(1), sorted_areas(2)));

  subplot(2,3,6); imshow(overlay);
  title('Contornos: blob1=vermelho  blob2=ciano');

  sgtitle(sprintf('Projeto 7 - Segmentacao: %s', images{idx}), 'FontSize', 12);
end

fprintf('\n=== Concluido ===\n');
