% main_project9.m
% PDI - Projeto 9: Filtros Butterworth e Filtro de Alta Enfase
% Autor: Matheus Serrao Uchoa
%
% Requer: highEnphasisFilt.m no mesmo diretorio
% Imagens: imgs_orig/woman.tif, imgs_orig/chestXray.tif

clear; clc; close all;

%% =========================================================
%  Parte (a): woman.tif - espectro e filtros Butterworth
%% =========================================================
fprintf('=== Parte (a): woman.tif ===\n');

f_w = double(imread('imgs_orig/woman.tif'));
[rows, cols] = size(f_w);

D0 = 60;   % frequencia de corte (pixels)
n  = 2;    % ordem do filtro

% Grade de frequencias centrada
[U, V] = meshgrid(-(cols/2):(cols/2-1), -(rows/2):(rows/2-1));
D = sqrt(U.^2 + V.^2);

% Filtros
H_LP = 1 ./ (1 + (D ./ D0).^(2*n));
H_HP = 1 - H_LP;

% Espectro original
F_w  = fftshift(fft2(f_w));
spec = log1p(abs(F_w));

% Filtragem
g_lp = real(ifft2(ifftshift(H_LP .* F_w)));
g_hp = real(ifft2(ifftshift(H_HP .* F_w)));

% --- exibicao ---
figure('Name', 'Parte (a) - woman.tif Butterworth');
subplot(2,3,1); imshow(uint8(f_w),[]); title('Original');
subplot(2,3,2); imagesc(spec); colormap(gca,'gray'); axis image off; title('Espectro log|F|');
subplot(2,3,3); imagesc(H_LP,[0 1]); colormap(gca,'hot'); axis image off;
  title(sprintf('Filtro PB D_0=%d n=%d',D0,n));
subplot(2,3,4); imshow(mat2gray(g_lp),[]); title('Saida PB');
subplot(2,3,5); imagesc(log1p(abs(fftshift(fft2(g_lp))))); colormap(gca,'gray'); axis image off;
  title('Espectro saida PB');
subplot(2,3,6); imshow(mat2gray(g_hp),[]); title('Saida PA');
sgtitle('Projeto 9 - Parte (a)');

% Visualizacao 3D do filtro PB
sz = 128;
[Us, Vs] = meshgrid(-(sz/2):(sz/2-1), -(sz/2):(sz/2-1));
Ds = sqrt(Us.^2 + Vs.^2);
D0s = D0 * sz / max(rows, cols);
Hs_lp = 1 ./ (1 + (Ds ./ D0s).^(2*n));
Hs_hp = 1 - Hs_lp;

figure('Name', 'Filtro PB 3D');
surf(Us, Vs, Hs_lp, 'EdgeColor', 'none');
colormap viridis; colorbar;
xlabel('u'); ylabel('v'); zlabel('H(u,v)');
title(sprintf('Filtro PB Butterworth 3D  D_0=%d  n=%d', D0, n));
view([-35,35]);

figure('Name', 'Filtro PA 3D');
surf(Us, Vs, Hs_hp, 'EdgeColor', 'none');
colormap plasma; colorbar;
xlabel('u'); ylabel('v'); zlabel('H(u,v)');
title(sprintf('Filtro PA Butterworth 3D  D_0=%d  n=%d', D0, n));
view([-35,35]);

%% =========================================================
%  Parte (b): funcao highEnphasisFilt - demonstracao
%% =========================================================
fprintf('=== Parte (b): highEnphasisFilt ===\n');

a = 0.5; b = 2.0;

[H_hfe, g_hfe] = highEnphasisFilt(a, b, f_w, 'butterworth', D0, n, true);

fprintf('  a=%.1f  b=%.1f  D0=%g  n=%d\n', a, b, D0, n);
fprintf('  H_HFE: min=%.4f  max=%.4f\n', min(H_hfe(:)), max(H_hfe(:)));

%% =========================================================
%  Parte (c): chestXray.tif - HEF + histEq
%% =========================================================
fprintf('=== Parte (c): chestXray.tif ===\n');

f_c = double(imread('imgs_orig/chestXray.tif'));
[cr, cc] = size(f_c);

D0c = 40; nc = 2; ac = 0.5; bc = 2.0;

[H_hfe_c, g_hfe_c] = highEnphasisFilt(ac, bc, f_c, 'butterworth', D0c, nc, false);

% Equalizacao de histograma
g_eq = histeq(g_hfe_c);

% Espectros
Fc_orig = fftshift(fft2(f_c));
Fc_filt = fftshift(fft2(double(g_hfe_c)));

% --- exibicao ---
figure('Name', 'Parte (c) - chestXray HEF + histEq');
subplot(2,3,1); imshow(uint8(f_c),[]); title('RX original');
subplot(2,3,2); imagesc(log1p(abs(Fc_orig))); colormap(gca,'gray'); axis image off;
  title('Espectro original');
subplot(2,3,3); imagesc(H_hfe_c); colormap(gca,'hot'); axis image off;
  title(sprintf('HEF D_0=%d a=%.1f b=%.1f', D0c, ac, bc));
subplot(2,3,4); imshow(g_hfe_c,[]); title('Apos HEF');
subplot(2,3,5); imagesc(log1p(abs(Fc_filt))); colormap(gca,'gray'); axis image off;
  title('Espectro apos HEF');
subplot(2,3,6); imshow(g_eq,[]); title('HEF + histEq');
sgtitle('Projeto 9 - Parte (c)');

% Histogramas
figure('Name', 'Histogramas parte (c)');
subplot(1,3,1); histogram(f_c(:),256); title('Hist: original'); xlabel('Intensidade');
subplot(1,3,2); histogram(double(g_hfe_c(:)),256); title('Hist: apos HEF'); xlabel('Intensidade');
subplot(1,3,3); histogram(double(g_eq(:)),256); title('Hist: HEF + histEq'); xlabel('Intensidade');
sgtitle('Parte (c): Histogramas');

fprintf('=== Concluido ===\n');
